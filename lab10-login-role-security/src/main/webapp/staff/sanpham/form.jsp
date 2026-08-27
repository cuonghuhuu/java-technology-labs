<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${isEdit ? 'Chỉnh Sửa Sản Phẩm' : 'Thêm Sản Phẩm Mới'} - Lab 10" />
<c:set var="activeGroup" value="business" />
<c:set var="activePage" value="sanpham" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <div class="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                    <div class="bg-warning-subtle text-warning rounded-circle p-3 fs-3">
                        <i class="bi ${isEdit ? 'bi-box-seam' : 'bi-bag-plus-fill'}"></i>
                    </div>
                    <div>
                        <h4 class="fw-bold mb-0">${isEdit ? 'Chỉnh Sửa Thông Tin Sản Phẩm' : 'Thêm Sản Phẩm Mới Vào Kho'}</h4>
                        <p class="text-muted small mb-0">Quản lý danh mục hàng hóa và số lượng tồn kho với JPA</p>
                    </div>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/staff/san-pham?action=${isEdit ? 'update' : 'create'}">
                    <c:if test="${isEdit}">
                        <input type="hidden" name="id" value="${sanPham.id}">
                    </c:if>

                    <div class="mb-3">
                        <label for="maSanPham" class="form-label">Mã Sản Phẩm <span class="text-danger">*</span></label>
                        <input type="text" class="form-control ${not empty errors.maSanPham ? 'is-invalid' : ''}"
                               id="maSanPham" name="maSanPham" value="<c:out value='${sanPham.maSanPham}'/>"
                               placeholder="vd: SP001" required>
                        <c:if test="${not empty errors.maSanPham}">
                            <div class="invalid-feedback">${errors.maSanPham}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="tenSanPham" class="form-label">Tên Sản Phẩm <span class="text-danger">*</span></label>
                        <input type="text" class="form-control ${not empty errors.tenSanPham ? 'is-invalid' : ''}"
                               id="tenSanPham" name="tenSanPham" value="<c:out value='${sanPham.tenSanPham}'/>"
                               placeholder="vd: Laptop Dell XPS 15" required>
                        <c:if test="${not empty errors.tenSanPham}">
                            <div class="invalid-feedback">${errors.tenSanPham}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="danhMuc" class="form-label">Danh Mục Hàng Hóa</label>
                        <input type="text" class="form-control" id="danhMuc" name="danhMuc"
                               value="<c:out value='${sanPham.danhMuc}'/>" placeholder="vd: Máy tính xách tay, Phụ kiện...">
                    </div>

                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label for="gia" class="form-label">Đơn Giá (VNĐ)</label>
                            <input type="number" step="1000" min="0" class="form-control ${not empty errors.gia ? 'is-invalid' : ''}"
                                   id="gia" name="gia" value="${sanPham.gia != null ? sanPham.gia : 0}">
                            <c:if test="${not empty errors.gia}">
                                <div class="invalid-feedback">${errors.gia}</div>
                            </c:if>
                        </div>
                        <div class="col-md-6">
                            <label for="soLuong" class="form-label">Số Lượng Tồn Kho</label>
                            <input type="number" min="0" class="form-control ${not empty errors.soLuong ? 'is-invalid' : ''}"
                                   id="soLuong" name="soLuong" value="${sanPham.soLuong != null ? sanPham.soLuong : 0}">
                            <c:if test="${not empty errors.soLuong}">
                                <div class="invalid-feedback">${errors.soLuong}</div>
                            </c:if>
                        </div>
                    </div>

                    <div class="mb-4">
                        <label for="moTa" class="form-label">Mô Tả Sản Phẩm</label>
                        <textarea class="form-control" id="moTa" name="moTa" rows="3"
                                  placeholder="Nhập thông tin cấu hình, đặc điểm chi tiết..."><c:out value='${sanPham.moTa}'/></textarea>
                    </div>

                    <div class="d-flex gap-2 justify-content-end">
                        <a href="${pageContext.request.contextPath}/staff/san-pham" class="btn btn-outline-secondary px-4 rounded-pill">
                            Hủy bỏ
                        </a>
                        <button type="submit" class="btn btn-warning text-dark px-4 rounded-pill shadow-sm">
                            <i class="bi bi-floppy me-1"></i> ${isEdit ? 'Lưu Thay Đổi' : 'Thêm Sản Phẩm'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
