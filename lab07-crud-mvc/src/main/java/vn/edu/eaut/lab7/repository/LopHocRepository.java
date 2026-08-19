package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.LopHoc;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LopHocRepository {
    private static final List<LopHoc> data = new ArrayList<>();
    private static int autoId = 4;
    static {
        data.add(new LopHoc(1, "DCCNTT15.10.1", "Công nghệ thông tin 1", "Nguyễn Văn Nam", 35));
        data.add(new LopHoc(2, "DCCNTT15.10.2", "Công nghệ thông tin 2", "Trần Thị Mai", 38));
        data.add(new LopHoc(3, "DCCNTT15.10.3", "Công nghệ thông tin 3", "Lê Minh Tuấn", 32));
    }
    public List<LopHoc> findAll() { return new ArrayList<>(data); }
    public LopHoc findById(int id) { return data.stream().filter(l -> l.getId() == id).findFirst().orElse(null); }
    public void add(LopHoc lopHoc) { lopHoc.setId(autoId++); data.add(lopHoc); }
    public void update(LopHoc lopHoc) {
        LopHoc old = findById(lopHoc.getId());
        if (old != null) { old.setMaLop(lopHoc.getMaLop()); old.setTenLop(lopHoc.getTenLop()); old.setCoVanHocTap(lopHoc.getCoVanHocTap()); old.setSoLuongSinhVien(lopHoc.getSoLuongSinhVien()); }
    }
    public void delete(int id) { data.removeIf(l -> l.getId() == id); }
    public List<LopHoc> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        String key = keyword.trim().toLowerCase();
        return data.stream().filter(l -> l.getMaLop().toLowerCase().contains(key) || l.getTenLop().toLowerCase().contains(key)).collect(Collectors.toList());
    }
}
