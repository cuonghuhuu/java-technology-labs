package vn.edu.eaut.lab5.bus;
import vn.edu.eaut.lab5.dal.TaiKhoanDAL; import vn.edu.eaut.lab5.model.TaiKhoan; import java.sql.SQLException;
public class TaiKhoanBUS {private final TaiKhoanDAL dal=new TaiKhoanDAL();public TaiKhoan login(String u,String p)throws SQLException{if(u==null||u.isBlank()||p==null||p.isBlank())throw new IllegalArgumentException("Vui lòng nhập tài khoản và mật khẩu");return dal.login(u.trim(),p);}}
