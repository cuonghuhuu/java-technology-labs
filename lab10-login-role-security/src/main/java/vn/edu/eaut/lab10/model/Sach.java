package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sach")
public class Sach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_sach", nullable = false, unique = true, length = 30)
    private String maSach;

    @Column(name = "ten_sach", nullable = false, length = 150)
    private String tenSach;

    @Column(name = "tac_gia", nullable = false, length = 100)
    private String tacGia;

    @Column(name = "the_loai", length = 50)
    private String theLoai;

    @Column(name = "nam_xuat_ban")
    private Integer namXuatBan;

    @Column(name = "gia")
    private Double gia = 0.0;

    @Column(name = "so_luong")
    private Integer soLuong = 0;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String tacGia, String theLoai, Integer namXuatBan, Double gia, Integer soLuong) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.namXuatBan = namXuatBan;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public Sach(Integer id, String maSach, String tenSach, String tacGia, String theLoai, Integer namXuatBan, Double gia, Integer soLuong) {
        this.id = id;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.namXuatBan = namXuatBan;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
