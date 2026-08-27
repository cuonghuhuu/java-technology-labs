<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${isEdit ? 'Chỉnh Sửa Sách' : 'Thêm Sách Mới'} - Lab 10" />
<c:set var="activeGroup" value="business" />
<c:set var="activePage" value="sach" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <div class="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                    <div class="bg-success-subtle text-success rounded-circle p-3 fs-3">
                        <i class="bi ${isEdit ? 'bi-journal-check' : 'bi-journal-plus'}"></i>
                    </div>
                    <div>
                        <h4 class="fw-bold mb-0">${isEdit ? 'Chỉnh Sửa Thông Tin Sách' : 'Thêm Sách Mới Vào Kho'}</h4>
                        <p class="text-muted small mb-0">Quản lý kho sách và thông tin xuất bản với JPA</p>
                    </div>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/staff/sach?action=${isEdit ? 'update' : 'create'}">
                    <c:if test="${isEdit}">
                        <input type="hidden" name="id" value="${sach.id}">
                    </c:if>

                    <div class="mb-3">
                        <label for="maSach" class="form-label">Mã Sách <span class="text-danger">*</span></label>
                        <input type="text" class="form-control ${not empty errors.maSach ? 'is-invalid' : ''}"
                               id="maSach" name="maSach" value="<c:out value='${sach.maSach}'/>"
                               placeholder="vd: BK001" required>
                        <c:if test="${not empty errors.maSach}">
                            <div class="invalid-feedback">${errors.maSach}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="tenSach" class="form-label">Tên Sách <span class="text-danger">*</span></label>
                        <input type="text" class="form-control ${not empty errors.tenSach ? 'is-invalid' : ''}"
                               id="tenSach" name="tenSach" value="<c:out value='${sach.tenSach}'/>"
                               placeholder="vd: Lập trình Java Nâng cao" required>
                        <c:if test="${not empty errors.tenSach}">
                            <div class="invalid-feedback">${errors.tenSach}</div>
                        </c:if>
                    </div>

                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label for="tacGia" class="form-label">Tác Giả <span class="text-danger">*</span></label>
                            <input type="text" class="form-control ${not empty errors.tacGia ? 'is-invalid' : ''}"
                                   id="tacGia" name="tacGia" value="<c:out value='${sach.tacGia}'/>"
                                   placeholder="vd: GS. Nguyễn Văn A" required>
                            <c:if test="${not empty errors.tacGia}">
                                <div class="invalid-feedback">${errors.tacGia}</div>
                            </c:if>
                        </div>
                        <div class="col-md-6">
                            <label for="theLoai" class="form-label">Thể Loại</label>
                            <input type="text" class="form-control"
                                   id="theLoai" name="theLoai" value="<c:out value='${sach.theLoai}'/>"
                                   placeholder="vd: Công nghệ thông tin">
                        </div>
                    </div>

                    <div class="row g-3 mb-4">
                        <div class="col-md-4">
                            <label for="namXuatBan" class="form-label">Năm XB</label>
                            <input type="number" min="1900" max="2100" class="form-control"
                                   id="namXuatBan" name="namXuatBan" value="${sach.namXuatBan != null ? sach.namXuatBan : 2024}">
                        </div>
                        <div class="col-md-4">
                            <label for="gia" class="form-label">Đơn Giá (VNĐ)</label>
                            <input type="number" step="1000" min="0" class="form-control ${not empty errors.gia ? 'is-invalid' : ''}"
                                   id="gia" name="gia" value="${sach.gia != null ? sach.gia : 0}">
                            <c:if test="${not empty errors.gia}">
                                <div class="invalid-feedback">${errors.gia}</div>
                            </c:if>
                        </div>
                        <div class="col-md-4">
                            <label for="soLuong" class="form-label">Số Lượng</label>
                            <input type="number" min="0" class="form-control ${not empty errors.soLuong ? 'is-invalid' : ''}"
                                   id="soLuong" name="soLuong" value="${sach.soLuong != null ? sach.soLuong : 0}">
                            <c:if test="${not empty errors.soLuong}">
                                <div class="invalid-feedback">${errors.soLuong}</div>
                            </c:if>
                        </div>
                    </div>

                    <div class="d-flex gap-2 justify-content-end">
                        <a href="${pageContext.request.contextPath}/staff/sach" class="btn btn-outline-secondary px-4 rounded-pill">
                            Hủy bỏ
                        </a>
                        <button type="submit" class="btn btn-success px-4 rounded-pill shadow-sm">
                            <i class="bi bi-floppy me-1"></i> ${isEdit ? 'Lưu Thay Đổi' : 'Thêm Sách'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
