package vn.edu.eaut.lab7.model;

public class DiemSinhVien {
    private int id;
    private String maSinhVien;
    private String hoTen;
    private double diemChuyenCan;
    private double diemGiuaKy;
    private double diemCuoiKy;

    public DiemSinhVien() { }
    public DiemSinhVien(int id, String maSinhVien, String hoTen, double diemChuyenCan, double diemGiuaKy, double diemCuoiKy) {
        this.id = id; this.maSinhVien = maSinhVien; this.hoTen = hoTen;
        this.diemChuyenCan = diemChuyenCan; this.diemGiuaKy = diemGiuaKy; this.diemCuoiKy = diemCuoiKy;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMaSinhVien() { return maSinhVien; }
    public void setMaSinhVien(String maSinhVien) { this.maSinhVien = maSinhVien; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public double getDiemChuyenCan() { return diemChuyenCan; }
    public void setDiemChuyenCan(double diemChuyenCan) { this.diemChuyenCan = diemChuyenCan; }
    public double getDiemGiuaKy() { return diemGiuaKy; }
    public void setDiemGiuaKy(double diemGiuaKy) { this.diemGiuaKy = diemGiuaKy; }
    public double getDiemCuoiKy() { return diemCuoiKy; }
    public void setDiemCuoiKy(double diemCuoiKy) { this.diemCuoiKy = diemCuoiKy; }
    public double getDiemTongKet() { return Math.round((diemChuyenCan * 0.1 + diemGiuaKy * 0.3 + diemCuoiKy * 0.6) * 100.0) / 100.0; }
    public String getXepLoai() {
        double diem = getDiemTongKet();
        if (diem >= 8.5) return "A";
        if (diem >= 7.0) return "B";
        if (diem >= 5.5) return "C";
        if (diem >= 4.0) return "D";
        return "F";
    }
}
