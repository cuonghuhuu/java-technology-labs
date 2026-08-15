package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.SanPhamDAL;
import vn.edu.eaut.lab5.model.SanPham;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SanPhamBUS {

    private final SanPhamDAL sanPhamDAL =
            new SanPhamDAL();

    public List<SanPham> findAll()
            throws SQLException {

        return sanPhamDAL.findAll();
    }

    public List<SanPham> searchByName(String keyword)
            throws SQLException {

        return sanPhamDAL.searchByName(keyword);
    }

    public List<SanPham> search(String name, BigDecimal from, BigDecimal to, Integer stockFrom,
                                Integer stockTo, Integer maDm, String sort, int limit, int offset) throws SQLException {
        return sanPhamDAL.search(name, from, to, stockFrom, stockTo, maDm, sort, limit, offset);
    }
    public int count(String name, BigDecimal from, BigDecimal to, Integer stockFrom, Integer stockTo, Integer maDm) throws SQLException {
        return sanPhamDAL.count(name, from, to, stockFrom, stockTo, maDm);
    }

    public boolean save(SanPham sp)
            throws SQLException {

        validate(sp);

        if (sp.getMaSp() == 0) {
            return sanPhamDAL.insert(sp);
        }

        return sanPhamDAL.update(sp);
    }

    public boolean delete(int maSp)
            throws SQLException {

        if (maSp <= 0) {
            throw new IllegalArgumentException(
                    "Mã sản phẩm không hợp lệ"
            );
        }

        return sanPhamDAL.delete(maSp);
    }

    private void validate(SanPham sp) {

        if (sp.getTenSp() == null
                || sp.getTenSp().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Tên sản phẩm không được rỗng"
            );
        }

        if (sp.getDonGia() == null
                || sp.getDonGia()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Đơn giá phải lớn hơn 0"
            );
        }

        if (sp.getSoLuong() < 0) {

            throw new IllegalArgumentException(
                    "Số lượng không được âm"
            );
        }
    }
}
