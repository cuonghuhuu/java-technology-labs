package vn.edu.eaut.lab7.model;

public class LopHoc {
    private int id;
    private String maLop;
    private String tenLop;
    private String coVanHocTap;
    private int soLuongSinhVien;

    public LopHoc() { }
    public LopHoc(int id, String maLop, String tenLop, String coVanHocTap, int soLuongSinhVien) {
        this.id = id; this.maLop = maLop; this.tenLop = tenLop;
        this.coVanHocTap = coVanHocTap; this.soLuongSinhVien = soLuongSinhVien;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }
    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }
    public String getCoVanHocTap() { return coVanHocTap; }
    public void setCoVanHocTap(String coVanHocTap) { this.coVanHocTap = coVanHocTap; }
    public int getSoLuongSinhVien() { return soLuongSinhVien; }
    public void setSoLuongSinhVien(int soLuongSinhVien) { this.soLuongSinhVien = soLuongSinhVien; }
}
