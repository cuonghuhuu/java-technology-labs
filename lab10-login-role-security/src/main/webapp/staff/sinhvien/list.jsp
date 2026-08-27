<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quản Lý Sinh Viên - Lab 10" />
<c:set var="activeGroup" value="business" />
<c:set var="activePage" value="sinhvien" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <!-- Header -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4 gap-3">
        <div>
            <h3 class="fw-bold text-dark mb-1">
                <i class="bi bi-people-fill text-primary me-2"></i>Quản Lý Sinh Viên (Module 1 - JPA)
            </h3>
            <p class="text-muted small mb-0">Quản lý hồ sơ sinh viên, thông tin lớp học và điểm GPA với Hibernate ORM</p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/staff/sinh-vien?action=new" class="btn btn-primary px-3 py-2 rounded-pill shadow-sm">
                <i class="bi bi-person-plus-fill me-1"></i> Thêm Sinh Viên Mới
            </a>
        </div>
    </div>

    <!-- Alert Notifications -->
    <c:if test="${param.msg == 'create_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Thêm mới sinh viên thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'update_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Cập nhật thông tin sinh viên thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'delete_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Đã xóa sinh viên khỏi hệ thống!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Search Card -->
    <div class="card border-0 shadow-sm rounded-4 p-3 mb-4 bg-white">
        <form method="get" action="${pageContext.request.contextPath}/staff/sinh-vien" class="row g-3 align-items-center">
            <div class="col-md-8 col-lg-6">
                <div class="input-group">
                    <span class="input-group-text bg-light border-end-0"><i class="bi bi-search text-muted"></i></span>
                    <input type="text" name="keyword" class="form-control bg-light border-start-0"
                           placeholder="Tìm kiếm theo mã SV, họ tên, email, lớp..." value="<c:out value='${keyword}'/>">
                </div>
            </div>
            <div class="col-md-4 col-lg-2">
                <button type="submit" class="btn btn-primary w-100 rounded-pill">
                    <i class="bi bi-search me-1"></i> Tìm kiếm
                </button>
            </div>
            <c:if test="${not empty keyword}">
                <div class="col-md-4 col-lg-2">
                    <a href="${pageContext.request.contextPath}/staff/sinh-vien" class="btn btn-outline-secondary w-100 rounded-pill">
                        <i class="bi bi-x-circle me-1"></i> Xóa tìm kiếm
                    </a>
                </div>
            </c:if>
        </form>
    </div>

    <!-- Student Table -->
    <div class="card border-0 shadow-sm rounded-4 overflow-hidden bg-white">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Mã SV</th>
                        <th>Họ và Tên</th>
                        <th>Email</th>
                        <th>Lớp Học</th>
                        <th>Điểm GPA</th>
                        <th>Xếp Loại</th>
                        <th>Ngày Sinh</th>
                        <th class="text-end pe-4">Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="sv" items="${sinhVienList}">
                        <tr>
                            <td class="ps-4 fw-bold text-secondary">#${sv.id}</td>
                            <td><span class="badge bg-light text-dark border"><code>${sv.maSinhVien}</code></span></td>
                            <td class="fw-semibold text-dark">${sv.hoTen}</td>
                            <td><small class="text-muted">${sv.email}</small></td>
                            <td><span class="badge bg-secondary-subtle text-secondary">${sv.lop}</span></td>
                            <td><strong class="text-primary">${sv.gpa}</strong></td>
                            <td>
                                <span class="badge rounded-pill
                                    <c:choose>
                                        <c:when test="${sv.gpa >= 3.6}">bg-success</c:when>
                                        <c:when test="${sv.gpa >= 3.2}">bg-primary</c:when>
                                        <c:when test="${sv.gpa >= 2.5}">bg-info text-dark</c:when>
                                        <c:when test="${sv.gpa >= 2.0}">bg-warning text-dark</c:when>
                                        <c:otherwise>bg-danger</c:otherwise>
                                    </c:choose>">
                                    ${sv.xepLoai}
                                </span>
                            </td>
                            <td class="small text-muted">${sv.ngaySinh}</td>
                            <td class="text-end pe-4">
                                <div class="btn-group btn-group-sm">
                                    <a href="${pageContext.request.contextPath}/staff/sinh-vien?action=edit&id=${sv.id}"
                                       class="btn btn-outline-primary" title="Chỉnh sửa">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/staff/sinh-vien?action=delete&id=${sv.id}"
                                       class="btn btn-outline-danger" title="Xóa"
                                       onclick="return confirmDelete(event, 'Bạn có chắc chắn muốn xóa sinh viên [${sv.hoTen}] không?')">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty sinhVienList}">
                        <tr>
                            <td colspan="9" class="text-center py-4 text-muted">
                                <i class="bi bi-inbox fs-2 d-block mb-2"></i>
                                Chưa có sinh viên nào trong danh sách.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
