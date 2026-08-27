package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.Sach;
import vn.edu.eaut.lab10.repository.SachRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SachService {

    private final SachRepository repository = new SachRepository();

    public List<Sach> findAll() {
        return repository.findAll();
    }

    public Sach findById(Integer id) {
        return repository.findById(id);
    }

    public Sach findByMa(String ma) {
        return repository.findByMa(ma);
    }

    public List<Sach> search(String keyword) {
        return repository.search(keyword);
    }

    public Map<String, String> validate(Sach sach, boolean isUpdate) {
        Map<String, String> errors = new HashMap<>();

        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
            errors.put("maSach", "Mã sách không được để trống.");
        } else if (repository.existsByMa(sach.getMaSach().trim(), isUpdate ? sach.getId() : null)) {
            errors.put("maSach", "Mã sách đã tồn tại trong hệ thống.");
        }

        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
            errors.put("tenSach", "Tên sách không được để trống.");
        }

        if (sach.getTacGia() == null || sach.getTacGia().trim().isEmpty()) {
            errors.put("tacGia", "Tác giả không được để trống.");
        }

        if (sach.getGia() != null && sach.getGia() < 0) {
            errors.put("gia", "Giá sách không được là số âm.");
        }

        if (sach.getSoLuong() != null && sach.getSoLuong() < 0) {
            errors.put("soLuong", "Số lượng không được là số âm.");
        }

        return errors;
    }

    public void save(Sach sach) {
        repository.save(sach);
    }

    public void update(Sach sach) {
        repository.update(sach);
    }

    public void delete(Integer id) {
        repository.delete(id);
    }

    public long count() {
        return repository.count();
    }
}
