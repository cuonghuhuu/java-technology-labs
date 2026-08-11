package vn.edu.eaut.lab3;

public class Student {

    private String maSinhVien;
    private String hoTen;
    private double diemTrungBinh;

    public Student(String maSinhVien, String hoTen, double diemTrungBinh) {
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getXepLoai() {

        if (diemTrungBinh >= 8.5) {
            return "Giỏi";
        }

        if (diemTrungBinh >= 7.0) {
            return "Khá";
        }

        if (diemTrungBinh >= 5.0) {
            return "Trung bình";
        }

        return "Yếu";
    }
}