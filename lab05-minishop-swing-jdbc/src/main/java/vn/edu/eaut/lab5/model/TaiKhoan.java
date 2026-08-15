package vn.edu.eaut.lab5.model;

public class TaiKhoan {
    private String username, password, hoTen, vaiTro;
    public TaiKhoan() {}
    public TaiKhoan(String username, String password, String hoTen, String vaiTro) {
        this.username = username; this.password = password; this.hoTen = hoTen; this.vaiTro = vaiTro;
    }
    public String getUsername() { return username; }
    public void setUsername(String v) { username = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { password = v; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String v) { hoTen = v; }
    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String v) { vaiTro = v; }
}
