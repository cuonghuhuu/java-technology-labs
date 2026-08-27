<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${isEdit ? 'Chỉnh Sửa Sinh Viên' : 'Thêm Sinh Viên Mới'} - Lab 10" />
<c:set var="activeGroup" value="business" />
<c:set var="activePage" value="sinhvien" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <div class="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                    <div class="bg-primary-subtle text-primary rounded-circle p-3 fs-3">
                        <i class="bi ${isEdit ? 'bi-person-gear' : 'bi-person-plus-fill'}"></i>
                    </div>
                    <div>
                        <h4 class="fw-bold mb-0">${isEdit ? 'Chỉnh Sửa Thông Tin Sinh Viên' : 'Thêm Sinh Viên Mới'}</h4>
                        <p class="text-muted small mb-0">Quản lý dữ liệu sinh viên trong cơ sở dữ liệu với JPA</p>
                    </div>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/staff/sinh-vien?action=${isEdit ? 'update' : 'create'}">
                    <c:if test="${isEdit}">
                        <input type="hidden" name="id" value="${sinhVien.id}">
                    </c:if>

                    <div class="mb-3">
                        <label for="maSinhVien" class="form-label">Mã Sinh Viên <span class="text-danger">*</span></label>
                        <input type="text" class="form-control ${not empty errors.maSinhVien ? 'is-invalid' : ''}"
                               id="maSinhVien" name="maSinhVien" value="<c:out value='${sinhVien.maSinhVien}'/>"
                               placeholder="vd: SV001 hoặc 20240001" required>
                        <c:if test="${not empty errors.maSinhVien}">
                            <div class="invalid-feedback">${errors.maSinhVien}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="hoTen" class="form-label">Họ và Tên <span class="text-danger">*</span></label>
                        <input type="text" class="form-control ${not empty errors.hoTen ? 'is-invalid' : ''}"
                               id="hoTen" name="hoTen" value="<c:out value='${sinhVien.hoTen}'/>"
                               placeholder="vd: Nguyễn Văn A" required>
                        <c:if test="${not empty errors.hoTen}">
                            <div class="invalid-feedback">${errors.hoTen}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Địa chỉ Email <span class="text-danger">*</span></label>
                        <input type="email" class="form-control ${not empty errors.email ? 'is-invalid' : ''}"
                               id="email" name="email" value="<c:out value='${sinhVien.email}'/>"
                               placeholder="vd: an.nv@eaut.edu.vn" required>
                        <c:if test="${not empty errors.email}">
                            <div class="invalid-feedback">${errors.email}</div>
                        </c:if>
                    </div>

                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label for="lop" class="form-label">Lớp Học <span class="text-danger">*</span></label>
                            <input type="text" class="form-control ${not empty errors.lop ? 'is-invalid' : ''}"
                                   id="lop" name="lop" value="<c:out value='${sinhVien.lop}'/>"
                                   placeholder="vd: DCCNTT15.10.1" required>
                            <c:if test="${not empty errors.lop}">
                                <div class="invalid-feedback">${errors.lop}</div>
                            </c:if>
                        </div>
                        <div class="col-md-6">
                            <label for="gpa" class="form-label">Điểm GPA (Thang 4.0)</label>
                            <input type="number" step="0.01" min="0" max="4"
                                   class="form-control ${not empty errors.gpa ? 'is-invalid' : ''}"
                                   id="gpa" name="gpa" value="${sinhVien.gpa != null ? sinhVien.gpa : 0.0}">
                            <c:if test="${not empty errors.gpa}">
                                <div class="invalid-feedback">${errors.gpa}</div>
                            </c:if>
                        </div>
                    </div>

                    <div class="mb-4">
                        <label for="ngaySinh" class="form-label">Ngày Sinh</label>
                        <input type="date" class="form-control" id="ngaySinh" name="ngaySinh"
                               value="${sinhVien.ngaySinh}">
                    </div>

                    <div class="d-flex gap-2 justify-content-end">
                        <a href="${pageContext.request.contextPath}/staff/sinh-vien" class="btn btn-outline-secondary px-4 rounded-pill">
                            Hủy bỏ
                        </a>
                        <button type="submit" class="btn btn-primary px-4 rounded-pill shadow-sm">
                            <i class="bi bi-floppy me-1"></i> ${isEdit ? 'Lưu Thay Đổi' : 'Thêm Sinh Viên'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
