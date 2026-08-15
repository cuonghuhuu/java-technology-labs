package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;

public class ThongKeData {
    private BigDecimal doanhThu = BigDecimal.ZERO;
    private HoaDon hoaDonLonNhat;
    private String sanPhamBanChay;
    private int soLuongBanChay;
    public BigDecimal getDoanhThu() { return doanhThu; }
    public void setDoanhThu(BigDecimal v) { doanhThu = v; }
    public HoaDon getHoaDonLonNhat() { return hoaDonLonNhat; }
    public void setHoaDonLonNhat(HoaDon v) { hoaDonLonNhat = v; }
    public String getSanPhamBanChay() { return sanPhamBanChay; }
    public void setSanPhamBanChay(String v) { sanPhamBanChay = v; }
    public int getSoLuongBanChay() { return soLuongBanChay; }
    public void setSoLuongBanChay(int v) { soLuongBanChay = v; }
}
