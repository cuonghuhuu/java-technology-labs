package vn.edu.eaut.lab7.model;

public class SanPham {
    private int id;
    private String ma;
    private String ten;
    private String moTa;
    private double gia;
    private int soLuong;

    public SanPham() { }

    public SanPham(int id, String ma, String ten, String moTa, double gia, int soLuong) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.moTa = moTa;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}
