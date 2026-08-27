package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sinh_vien")
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_sinh_vien", nullable = false, unique = true, length = 30)
    private String maSinhVien;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String lop;

    @Column(name = "gpa")
    private Double gpa = 0.0;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    public SinhVien() {
    }

    public SinhVien(String maSinhVien, String hoTen, String email, String lop, Double gpa, LocalDate ngaySinh) {
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.email = email;
        this.lop = lop;
        this.gpa = gpa;
        this.ngaySinh = ngaySinh;
    }

    public SinhVien(Integer id, String maSinhVien, String hoTen, String email, String lop, Double gpa, LocalDate ngaySinh) {
        this.id = id;
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.email = email;
        this.lop = lop;
        this.gpa = gpa;
        this.ngaySinh = ngaySinh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getXepLoai() {
        if (gpa == null) return "Chưa xếp loại";
        if (gpa >= 3.6) return "Xuất sắc";
        if (gpa >= 3.2) return "Giỏi";
        if (gpa >= 2.5) return "Khá";
        if (gpa >= 2.0) return "Trung bình";
        return "Yếu";
    }
}
