package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.repository.SinhVienRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SinhVienService {

    private final SinhVienRepository repository = new SinhVienRepository();

    public List<SinhVien> findAll() {
        return repository.findAll();
    }

    public SinhVien findById(Integer id) {
        return repository.findById(id);
    }

    public SinhVien findByMa(String ma) {
        return repository.findByMa(ma);
    }

    public List<SinhVien> search(String keyword) {
        return repository.search(keyword);
    }

    public Map<String, String> validate(SinhVien sv, boolean isUpdate) {
        Map<String, String> errors = new HashMap<>();

        if (sv.getMaSinhVien() == null || sv.getMaSinhVien().trim().isEmpty()) {
            errors.put("maSinhVien", "Mã sinh viên không được để trống.");
        } else if (repository.existsByMa(sv.getMaSinhVien().trim(), isUpdate ? sv.getId() : null)) {
            errors.put("maSinhVien", "Mã sinh viên đã tồn tại trong hệ thống.");
        }

        if (sv.getHoTen() == null || sv.getHoTen().trim().isEmpty()) {
            errors.put("hoTen", "Họ tên không được để trống.");
        }

        if (sv.getEmail() == null || sv.getEmail().trim().isEmpty()) {
            errors.put("email", "Email không được để trống.");
        } else if (!sv.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "Email không đúng định dạng.");
        }

        if (sv.getLop() == null || sv.getLop().trim().isEmpty()) {
            errors.put("lop", "Lớp không được để trống.");
        }

        if (sv.getGpa() != null && (sv.getGpa() < 0.0 || sv.getGpa() > 4.0)) {
            errors.put("gpa", "Điểm GPA phải từ 0.0 đến 4.0.");
        }

        return errors;
    }

    public void save(SinhVien sv) {
        repository.save(sv);
    }

    public void update(SinhVien sv) {
        repository.update(sv);
    }

    public void delete(Integer id) {
        repository.delete(id);
    }

    public long count() {
        return repository.count();
    }
}
