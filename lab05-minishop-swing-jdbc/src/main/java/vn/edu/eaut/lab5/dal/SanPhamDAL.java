package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.SanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {
    private static final String COLUMNS = "sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, sp.ma_dm, dm.ten_dm";
    private SanPham map(ResultSet rs) throws SQLException {
        return new SanPham(rs.getInt("ma_sp"), rs.getString("ten_sp"), rs.getBigDecimal("don_gia"),
                rs.getInt("so_luong"), rs.getInt("ma_dm"), rs.getString("ten_dm"));
    }
    private String from() { return " FROM san_pham sp LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm "; }
    public List<SanPham> findAll() throws SQLException { return search("", null, null, null, null, null, "ma", Integer.MAX_VALUE, 0); }
    public List<SanPham> searchByName(String keyword) throws SQLException { return search(keyword, null, null, null, null, null, "ma", Integer.MAX_VALUE, 0); }
    public List<SanPham> search(String name, java.math.BigDecimal priceFrom, java.math.BigDecimal priceTo,
                                Integer stockFrom, Integer stockTo, Integer maDm, String sort, int limit, int offset) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ").append(COLUMNS).append(from()).append(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();
        if (name != null && !name.isBlank()) { sql.append("AND sp.ten_sp LIKE ? "); args.add("%" + name.trim() + "%"); }
        if (priceFrom != null) { sql.append("AND sp.don_gia >= ? "); args.add(priceFrom); }
        if (priceTo != null) { sql.append("AND sp.don_gia <= ? "); args.add(priceTo); }
        if (stockFrom != null) { sql.append("AND sp.so_luong >= ? "); args.add(stockFrom); }
        if (stockTo != null) { sql.append("AND sp.so_luong <= ? "); args.add(stockTo); }
        if (maDm != null && maDm > 0) { sql.append("AND sp.ma_dm = ? "); args.add(maDm); }
        String order = switch (sort == null ? "ma" : sort) { case "ten" -> "sp.ten_sp"; case "gia" -> "sp.don_gia"; default -> "sp.ma_sp"; };
        sql.append("ORDER BY ").append(order).append(" LIMIT ? OFFSET ?"); args.add(limit); args.add(offset);
        List<SanPham> list = new ArrayList<>();
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            bind(ps, args); try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } return list;
    }
    public int count(String name, java.math.BigDecimal priceFrom, java.math.BigDecimal priceTo, Integer stockFrom, Integer stockTo, Integer maDm) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM san_pham sp WHERE 1=1 "); List<Object> args = new ArrayList<>();
        if (name != null && !name.isBlank()) { sql.append("AND sp.ten_sp LIKE ? "); args.add("%" + name.trim() + "%"); }
        if (priceFrom != null) { sql.append("AND sp.don_gia >= ? "); args.add(priceFrom); }
        if (priceTo != null) { sql.append("AND sp.don_gia <= ? "); args.add(priceTo); }
        if (stockFrom != null) { sql.append("AND sp.so_luong >= ? "); args.add(stockFrom); }
        if (stockTo != null) { sql.append("AND sp.so_luong <= ? "); args.add(stockTo); }
        if (maDm != null && maDm > 0) { sql.append("AND sp.ma_dm = ? "); args.add(maDm); }
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql.toString())) { bind(ps,args); try(ResultSet rs=ps.executeQuery()){rs.next();return rs.getInt(1);} }
    }
    private void bind(PreparedStatement ps, List<Object> args) throws SQLException { for(int i=0;i<args.size();i++) ps.setObject(i+1,args.get(i)); }
    public boolean insert(SanPham sp) throws SQLException { String sql="INSERT INTO san_pham(ten_sp,don_gia,so_luong,ma_dm) VALUES(?,?,?,?)"; try(Connection c=DBHelper.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,sp.getTenSp());ps.setBigDecimal(2,sp.getDonGia());ps.setInt(3,sp.getSoLuong());if(sp.getMaDm()>0)ps.setInt(4,sp.getMaDm());else ps.setNull(4,Types.INTEGER);return ps.executeUpdate()>0;} }
    public boolean update(SanPham sp) throws SQLException { String sql="UPDATE san_pham SET ten_sp=?,don_gia=?,so_luong=?,ma_dm=? WHERE ma_sp=?"; try(Connection c=DBHelper.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,sp.getTenSp());ps.setBigDecimal(2,sp.getDonGia());ps.setInt(3,sp.getSoLuong());if(sp.getMaDm()>0)ps.setInt(4,sp.getMaDm());else ps.setNull(4,Types.INTEGER);ps.setInt(5,sp.getMaSp());return ps.executeUpdate()>0;} }
    public boolean delete(int maSp) throws SQLException { try(Connection c=DBHelper.getConnection();PreparedStatement ps=c.prepareStatement("DELETE FROM san_pham WHERE ma_sp=?")){ps.setInt(1,maSp);return ps.executeUpdate()>0;} }
}
