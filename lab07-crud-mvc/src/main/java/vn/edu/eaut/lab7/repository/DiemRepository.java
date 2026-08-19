package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.DiemSinhVien;
import java.util.ArrayList;
import java.util.List;

public class DiemRepository {
    private static final List<DiemSinhVien> data = new ArrayList<>();
    private static int autoId = 3;
    static {
        data.add(new DiemSinhVien(1, "20240001", "Nguyễn Văn An", 9, 8, 8.5));
        data.add(new DiemSinhVien(2, "20240002", "Trần Thị Bình", 8, 7, 7.5));
    }
    public List<DiemSinhVien> findAll() { return new ArrayList<>(data); }
    public DiemSinhVien findById(int id) { return data.stream().filter(d -> d.getId() == id).findFirst().orElse(null); }
    public void add(DiemSinhVien diem) { diem.setId(autoId++); data.add(diem); }
    public void update(DiemSinhVien diem) {
        DiemSinhVien old = findById(diem.getId());
        if (old != null) { old.setMaSinhVien(diem.getMaSinhVien()); old.setHoTen(diem.getHoTen()); old.setDiemChuyenCan(diem.getDiemChuyenCan()); old.setDiemGiuaKy(diem.getDiemGiuaKy()); old.setDiemCuoiKy(diem.getDiemCuoiKy()); }
    }
    public void delete(int id) { data.removeIf(d -> d.getId() == id); }
}
