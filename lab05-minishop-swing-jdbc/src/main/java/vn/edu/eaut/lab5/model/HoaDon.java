package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private int maHd;
    private int maKh;
    private String tenKh;
    private String sdtKh;
    private LocalDate ngayLap;
    private BigDecimal tongTien = BigDecimal.ZERO;
    private List<ChiTietHoaDon> chiTiet = new ArrayList<>();
    public int getMaHd() { return maHd; }
    public void setMaHd(int maHd) { this.maHd = maHd; }
    public int getMaKh() { return maKh; }
    public void setMaKh(int maKh) { this.maKh = maKh; }
    public String getTenKh() { return tenKh; }
    public void setTenKh(String tenKh) { this.tenKh = tenKh; }
    public String getSdtKh() { return sdtKh; }
    public void setSdtKh(String sdtKh) { this.sdtKh = sdtKh; }
    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public List<ChiTietHoaDon> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<ChiTietHoaDon> chiTiet) { this.chiTiet = chiTiet; }
}
