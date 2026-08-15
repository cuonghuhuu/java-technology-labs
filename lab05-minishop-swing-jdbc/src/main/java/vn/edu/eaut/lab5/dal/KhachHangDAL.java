package vn.edu.eaut.lab5.dal;
import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.KhachHang;
import java.sql.*; import java.util.*;
public class KhachHangDAL {
    private KhachHang map(ResultSet r)throws SQLException{return new KhachHang(r.getInt("ma_kh"),r.getString("ten_kh"),r.getString("sdt"),r.getString("dia_chi"));}
    public List<KhachHang> findAll()throws SQLException{return search("");}
    public List<KhachHang> search(String key)throws SQLException{List<KhachHang> a=new ArrayList<>();String sql="SELECT ma_kh,ten_kh,sdt,dia_chi FROM khach_hang WHERE ten_kh LIKE ? OR sdt LIKE ? OR dia_chi LIKE ? ORDER BY ma_kh";try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){String k="%"+(key==null?"":key.trim())+"%";p.setString(1,k);p.setString(2,k);p.setString(3,k);try(ResultSet r=p.executeQuery()){while(r.next())a.add(map(r));}}return a;}
    public boolean insert(KhachHang x)throws SQLException{return execute("INSERT INTO khach_hang(ten_kh,sdt,dia_chi) VALUES(?,?,?)",x,0);}
    public boolean update(KhachHang x)throws SQLException{return execute("UPDATE khach_hang SET ten_kh=?,sdt=?,dia_chi=? WHERE ma_kh=?",x,x.getMaKh());}
    private boolean execute(String sql,KhachHang x,int id)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,x.getTenKh());p.setString(2,x.getSdt());p.setString(3,x.getDiaChi());if(id>0)p.setInt(4,id);return p.executeUpdate()>0;}}
    public boolean delete(int id)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM khach_hang WHERE ma_kh=?")){p.setInt(1,id);return p.executeUpdate()>0;}}
}
