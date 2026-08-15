package vn.edu.eaut.lab5.dal;
import vn.edu.eaut.lab5.config.DBHelper; import vn.edu.eaut.lab5.model.DanhMuc; import java.sql.*; import java.util.*;
public class DanhMucDAL {
 public List<DanhMuc> findAll()throws SQLException{List<DanhMuc>a=new ArrayList<>();try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("SELECT ma_dm,ten_dm FROM danh_muc ORDER BY ten_dm");ResultSet r=p.executeQuery()){while(r.next())a.add(new DanhMuc(r.getInt(1),r.getString(2)));}return a;}
 public boolean insert(DanhMuc x)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO danh_muc(ten_dm) VALUES(?)")){p.setString(1,x.getTenDm());return p.executeUpdate()>0;}}
 public boolean update(DanhMuc x)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("UPDATE danh_muc SET ten_dm=? WHERE ma_dm=?")){p.setString(1,x.getTenDm());p.setInt(2,x.getMaDm());return p.executeUpdate()>0;}}
 public boolean delete(int id)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM danh_muc WHERE ma_dm=?")){p.setInt(1,id);return p.executeUpdate()>0;}}
 public List<DanhMuc> search(String key)throws SQLException{List<DanhMuc>a=new ArrayList<>();try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("SELECT ma_dm,ten_dm FROM danh_muc WHERE ten_dm LIKE ? ORDER BY ten_dm")){p.setString(1,"%"+key+"%");try(ResultSet r=p.executeQuery()){while(r.next())a.add(new DanhMuc(r.getInt(1),r.getString(2)));}}return a;}
}
