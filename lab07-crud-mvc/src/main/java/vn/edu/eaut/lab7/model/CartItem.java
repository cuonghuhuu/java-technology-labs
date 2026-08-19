package vn.edu.eaut.lab7.model;

public class CartItem {
    private int sanPhamId;
    private String maSanPham;
    private String tenSanPham;
    private double gia;
    private int soLuong;

    public CartItem() { }
    public CartItem(SanPham sanPham, int soLuong) {
        this.sanPhamId = sanPham.getId(); this.maSanPham = sanPham.getMa();
        this.tenSanPham = sanPham.getTen(); this.gia = sanPham.getGia(); this.soLuong = soLuong;
    }
    public int getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(int sanPhamId) { this.sanPhamId = sanPhamId; }
    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getThanhTien() { return gia * soLuong; }
}
