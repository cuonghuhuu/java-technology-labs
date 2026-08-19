package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.SanPham;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SanPhamRepository {
    private static final List<SanPham> data = new ArrayList<>();
    private static int autoId = 4;
    static {
        data.add(new SanPham(1, "SP001", "Chuột không dây", "Chuột Bluetooth", 250000, 20));
        data.add(new SanPham(2, "SP002", "Bàn phím cơ", "Bàn phím 87 phím", 750000, 12));
        data.add(new SanPham(3, "SP003", "Tai nghe", "Tai nghe chụp tai", 450000, 15));
    }
    public List<SanPham> findAll() { return new ArrayList<>(data); }
    public SanPham findById(int id) { return data.stream().filter(s -> s.getId() == id).findFirst().orElse(null); }
    public void add(SanPham sanPham) { sanPham.setId(autoId++); data.add(sanPham); }
    public void update(SanPham sanPham) {
        SanPham old = findById(sanPham.getId());
        if (old != null) {
            old.setMa(sanPham.getMa()); old.setTen(sanPham.getTen()); old.setMoTa(sanPham.getMoTa());
            old.setGia(sanPham.getGia()); old.setSoLuong(sanPham.getSoLuong());
        }
    }
    public void delete(int id) { data.removeIf(s -> s.getId() == id); }
    public List<SanPham> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        String key = keyword.trim().toLowerCase();
        return data.stream().filter(s -> s.getMa().toLowerCase().contains(key) || s.getTen().toLowerCase().contains(key) || s.getMoTa().toLowerCase().contains(key)).collect(Collectors.toList());
    }
}
