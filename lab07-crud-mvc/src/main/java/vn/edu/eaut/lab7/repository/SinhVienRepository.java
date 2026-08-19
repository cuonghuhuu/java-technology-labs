package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.SinhVien;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SinhVienRepository {

    private static final List<SinhVien> data = new ArrayList<>();
    private static int autoId = 6;

    static {
        data.add(new SinhVien(
                1,
                "20240001",
                "Nguyễn Văn An",
                "an@gmail.com",
                "DCCNTT15.10.1"
        ));

        data.add(new SinhVien(
                2,
                "20240002",
                "Trần Thị Bình",
                "binh@gmail.com",
                "DCCNTT15.10.2"
        ));

        data.add(new SinhVien(
                3,
                "20240003",
                "Lê Văn Cường",
                "cuong@gmail.com",
                "DCCNTT15.10.1"
        ));

        data.add(new SinhVien(
                4,
                "20240004",
                "Phạm Thu Hà",
                "ha@gmail.com",
                "DCCNTT15.10.3"
        ));

        data.add(new SinhVien(
                5,
                "20240005",
                "Hoàng Minh Đức",
                "duc@gmail.com",
                "DCCNTT15.10.2"
        ));
    }

    public List<SinhVien> findAll() {
        return new ArrayList<>(data);
    }

    public SinhVien findById(int id) {
        return data.stream()
                .filter(sv -> sv.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(SinhVien sinhVien) {
        sinhVien.setId(autoId++);
        data.add(sinhVien);
    }

    public void update(SinhVien sinhVien) {

        SinhVien old = findById(sinhVien.getId());

        if (old != null) {
            old.setMaSinhVien(sinhVien.getMaSinhVien());
            old.setHoTen(sinhVien.getHoTen());
            old.setEmail(sinhVien.getEmail());
            old.setLop(sinhVien.getLop());
        }
    }

    public void delete(int id) {
        data.removeIf(sv -> sv.getId() == id);
    }

    public List<SinhVien> search(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String key = keyword.trim().toLowerCase();

        return data.stream()
                .filter(sv ->
                        sv.getHoTen().toLowerCase().contains(key)
                                || sv.getLop().toLowerCase().contains(key)
                                || sv.getMaSinhVien().toLowerCase().contains(key)
                )
                .collect(Collectors.toList());
    }

    public boolean existsMaSinhVien(String maSinhVien, int ignoreId) {

        return data.stream()
                .anyMatch(sv ->
                        sv.getId() != ignoreId
                                && sv.getMaSinhVien()
                                     .equalsIgnoreCase(maSinhVien)
                );
    }
}