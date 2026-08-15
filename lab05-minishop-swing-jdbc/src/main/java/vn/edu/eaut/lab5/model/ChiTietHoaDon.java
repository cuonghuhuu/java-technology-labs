package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;

public class ChiTietHoaDon {
    private int maHd, maSp, soLuong;
    private String tenSp;
    private BigDecimal donGia = BigDecimal.ZERO, thanhTien = BigDecimal.ZERO;
    public ChiTietHoaDon() {}
    public ChiTietHoaDon(int maSp, String tenSp, int soLuong, BigDecimal donGia) {
        this.maSp = maSp; this.tenSp = tenSp; this.soLuong = soLuong; this.donGia = donGia;
        this.thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));
    }
    public int getMaHd() { return maHd; }
    public void setMaHd(int v) { maHd = v; }
    public int getMaSp() { return maSp; }
    public void setMaSp(int v) { maSp = v; }
    public String getTenSp() { return tenSp; }
    public void setTenSp(String v) { tenSp = v; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int v) { soLuong = v; }
    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal v) { donGia = v; }
    public BigDecimal getThanhTien() { return thanhTien; }
    public void setThanhTien(BigDecimal v) { thanhTien = v; }
}
