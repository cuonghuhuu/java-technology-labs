package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.SanPham;
import vn.edu.eaut.lab10.repository.SanPhamRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanPhamService {

    private final SanPhamRepository repository = new SanPhamRepository();

    public List<SanPham> findAll() {
        return repository.findAll();
    }

    public SanPham findById(Integer id) {
        return repository.findById(id);
    }

    public SanPham findByMa(String ma) {
        return repository.findByMa(ma);
    }

    public List<SanPham> search(String keyword) {
        return repository.search(keyword);
    }

    public Map<String, String> validate(SanPham sp, boolean isUpdate) {
        Map<String, String> errors = new HashMap<>();

        if (sp.getMaSanPham() == null || sp.getMaSanPham().trim().isEmpty()) {
            errors.put("maSanPham", "Mã sản phẩm không được để trống.");
        } else if (repository.existsByMa(sp.getMaSanPham().trim(), isUpdate ? sp.getId() : null)) {
            errors.put("maSanPham", "Mã sản phẩm đã tồn tại trong hệ thống.");
        }

        if (sp.getTenSanPham() == null || sp.getTenSanPham().trim().isEmpty()) {
            errors.put("tenSanPham", "Tên sản phẩm không được để trống.");
        }

        if (sp.getGia() != null && sp.getGia() < 0) {
            errors.put("gia", "Đơn giá không được là số âm.");
        }

        if (sp.getSoLuong() != null && sp.getSoLuong() < 0) {
            errors.put("soLuong", "Số lượng tồn kho không được là số âm.");
        }

        return errors;
    }

    public void save(SanPham sp) {
        repository.save(sp);
    }

    public void update(SanPham sp) {
        repository.update(sp);
    }

    public void delete(Integer id) {
        repository.delete(id);
    }

    public long count() {
        return repository.count();
    }
}
