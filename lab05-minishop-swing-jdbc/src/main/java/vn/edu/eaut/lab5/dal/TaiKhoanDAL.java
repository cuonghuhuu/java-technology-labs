package vn.edu.eaut.lab5.dal;
import vn.edu.eaut.lab5.config.DBHelper; import vn.edu.eaut.lab5.model.TaiKhoan; import java.sql.*;
public class TaiKhoanDAL {public TaiKhoan login(String u,String p)throws SQLException{String q="SELECT username,password,ho_ten,vai_tro FROM tai_khoan WHERE username=? AND password=?";try(Connection c=DBHelper.getConnection();PreparedStatement s=c.prepareStatement(q)){s.setString(1,u);s.setString(2,p);try(ResultSet r=s.executeQuery()){if(r.next())return new TaiKhoan(r.getString(1),r.getString(2),r.getString(3),r.getString(4));return null;}}}}
