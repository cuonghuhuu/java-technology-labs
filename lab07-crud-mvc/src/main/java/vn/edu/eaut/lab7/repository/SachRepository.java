package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.Sach;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SachRepository {
    private static final List<Sach> data = new ArrayList<>();
    private static int autoId = 4;
    static {
        data.add(new Sach(1, "S001", "Lập trình Java", "Nguyễn Văn A", "Giáo dục", 2023));
        data.add(new Sach(2, "S002", "Cơ sở dữ liệu", "Trần Thị B", "Khoa học", 2022));
        data.add(new Sach(3, "S003", "Lập trình Web", "Lê Văn C", "Thanh niên", 2024));
    }
    public List<Sach> findAll() { return new ArrayList<>(data); }
    public Sach findById(int id) { return data.stream().filter(s -> s.getId() == id).findFirst().orElse(null); }
    public void add(Sach sach) { sach.setId(autoId++); data.add(sach); }
    public void update(Sach sach) {
        Sach old = findById(sach.getId());
        if (old != null) {
            old.setMaSach(sach.getMaSach()); old.setTenSach(sach.getTenSach()); old.setTacGia(sach.getTacGia());
            old.setNhaXuatBan(sach.getNhaXuatBan()); old.setNamXuatBan(sach.getNamXuatBan());
        }
    }
    public void delete(int id) { data.removeIf(s -> s.getId() == id); }
    public List<Sach> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        String key = keyword.trim().toLowerCase();
        return data.stream().filter(s -> s.getTenSach().toLowerCase().contains(key) || s.getTacGia().toLowerCase().contains(key)).collect(Collectors.toList());
    }
}
