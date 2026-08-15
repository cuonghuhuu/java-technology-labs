package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class HoaDonDAL {
    public List<HoaDon> search(LocalDate from, LocalDate to, String customer, BigDecimal totalFrom,
                               BigDecimal totalTo, int limit, int offset) throws SQLException {
        StringBuilder q=new StringBuilder("SELECT h.ma_hd,h.ma_kh,h.ngay_lap,h.tong_tien,k.ten_kh,k.sdt FROM hoa_don h JOIN khach_hang k ON k.ma_kh=h.ma_kh WHERE 1=1 ");
        List<Object> a=new ArrayList<>(); if(from!=null){q.append("AND h.ngay_lap>=? ");a.add(Date.valueOf(from));} if(to!=null){q.append("AND h.ngay_lap<=? ");a.add(Date.valueOf(to));} if(customer!=null&&!customer.isBlank()){q.append("AND (k.ten_kh LIKE ? OR k.sdt LIKE ?) ");a.add("%"+customer.trim()+"%");a.add("%"+customer.trim()+"%");} if(totalFrom!=null){q.append("AND h.tong_tien>=? ");a.add(totalFrom);} if(totalTo!=null){q.append("AND h.tong_tien<=? ");a.add(totalTo);} q.append("ORDER BY h.ngay_lap DESC,h.ma_hd DESC LIMIT ? OFFSET ?");a.add(limit);a.add(offset);List<HoaDon> result=new ArrayList<>();try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(q.toString())){for(int i=0;i<a.size();i++)p.setObject(i+1,a.get(i));try(ResultSet r=p.executeQuery()){while(r.next())result.add(mapSummary(r));}}return result;
    }
    public int count(LocalDate from, LocalDate to, String customer, BigDecimal totalFrom, BigDecimal totalTo) throws SQLException {
        StringBuilder q=new StringBuilder("SELECT COUNT(*) FROM hoa_don h JOIN khach_hang k ON k.ma_kh=h.ma_kh WHERE 1=1 ");List<Object>a=new ArrayList<>();if(from!=null){q.append("AND h.ngay_lap>=? ");a.add(Date.valueOf(from));}if(to!=null){q.append("AND h.ngay_lap<=? ");a.add(Date.valueOf(to));}if(customer!=null&&!customer.isBlank()){q.append("AND (k.ten_kh LIKE ? OR k.sdt LIKE ?) ");a.add("%"+customer.trim()+"%");a.add("%"+customer.trim()+"%");}if(totalFrom!=null){q.append("AND h.tong_tien>=? ");a.add(totalFrom);}if(totalTo!=null){q.append("AND h.tong_tien<=? ");a.add(totalTo);}try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(q.toString())){for(int i=0;i<a.size();i++)p.setObject(i+1,a.get(i));try(ResultSet r=p.executeQuery()){r.next();return r.getInt(1);}}
    }
    private HoaDon mapSummary(ResultSet r)throws SQLException{HoaDon h=new HoaDon();h.setMaHd(r.getInt("ma_hd"));h.setMaKh(r.getInt("ma_kh"));h.setNgayLap(r.getDate("ngay_lap").toLocalDate());h.setTongTien(r.getBigDecimal("tong_tien"));h.setTenKh(r.getString("ten_kh"));h.setSdtKh(r.getString("sdt"));return h;}
    public int saveTransaction(HoaDon hd) throws SQLException {
        if (hd.getChiTiet() == null || hd.getChiTiet().isEmpty()) throw new IllegalArgumentException("Hóa đơn phải có ít nhất một sản phẩm");
        String insertHd="INSERT INTO hoa_don(ma_kh,ngay_lap,tong_tien) VALUES(?,?,?)";
        String insertCt="INSERT INTO chi_tiet_hoa_don(ma_hd,ma_sp,so_luong,don_gia,thanh_tien) VALUES(?,?,?,?,?)";
        String updateStock="UPDATE san_pham SET so_luong=so_luong-? WHERE ma_sp=? AND so_luong>=?";
        try(Connection c=DBHelper.getConnection()) {
            boolean old=c.getAutoCommit(); c.setAutoCommit(false);
            try(PreparedStatement h=c.prepareStatement(insertHd,Statement.RETURN_GENERATED_KEYS);
                PreparedStatement ct=c.prepareStatement(insertCt); PreparedStatement stock=c.prepareStatement(updateStock)) {
                h.setInt(1,hd.getMaKh()); h.setDate(2,Date.valueOf(hd.getNgayLap()==null?LocalDate.now():hd.getNgayLap())); h.setBigDecimal(3,hd.getTongTien()); h.executeUpdate();
                try(ResultSet keys=h.getGeneratedKeys()){if(!keys.next())throw new SQLException("Không lấy được mã hóa đơn");hd.setMaHd(keys.getInt(1));}
                for(ChiTietHoaDon x:hd.getChiTiet()) {
                    if(x.getSoLuong()<=0)throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
                    stock.setInt(1,x.getSoLuong());stock.setInt(2,x.getMaSp());stock.setInt(3,x.getSoLuong());
                    if(stock.executeUpdate()!=1)throw new SQLException("Sản phẩm " + x.getTenSp() + " không đủ tồn kho");
                    ct.setInt(1,hd.getMaHd());ct.setInt(2,x.getMaSp());ct.setInt(3,x.getSoLuong());ct.setBigDecimal(4,x.getDonGia());ct.setBigDecimal(5,x.getThanhTien());ct.addBatch();
                }
                ct.executeBatch(); c.commit(); c.setAutoCommit(old); return hd.getMaHd();
            } catch(Exception ex) { try{c.rollback();}catch(SQLException ignored){} throw ex; }
            finally { try{c.setAutoCommit(old);}catch(SQLException ignored){} }
        }
    }
}
