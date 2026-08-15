package vn.edu.eaut.lab5.bus;
import vn.edu.eaut.lab5.dal.KhachHangDAL; import vn.edu.eaut.lab5.model.KhachHang; import java.sql.SQLException; import java.util.List;
public class KhachHangBUS {
 private final KhachHangDAL dal=new KhachHangDAL();
 public List<KhachHang> findAll()throws SQLException{return dal.findAll();} public List<KhachHang> search(String s)throws SQLException{return dal.search(s);}
 public boolean save(KhachHang x)throws SQLException{validate(x);return x.getMaKh()>0?dal.update(x):dal.insert(x);}
 public boolean delete(int id)throws SQLException{if(id<=0)throw new IllegalArgumentException("Mã khách hàng không hợp lệ");return dal.delete(id);}
 private void validate(KhachHang x){if(x==null||x.getTenKh()==null||x.getTenKh().trim().isEmpty())throw new IllegalArgumentException("Tên khách hàng không được rỗng");String s=x.getSdt()==null?"":x.getSdt().trim();if(!s.matches("\\d{1,10}"))throw new IllegalArgumentException("Số điện thoại chỉ gồm 1 đến 10 chữ số");}
}
