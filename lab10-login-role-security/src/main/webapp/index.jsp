<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Trang Chủ - Hệ Thống Lab 10 EAUT" />
<c:set var="activePage" value="home" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-5">
    <!-- Hero Banner -->
    <div class="row align-items-center mb-5">
        <div class="col-lg-7">
            <span class="badge bg-primary-subtle text-primary px-3 py-2 rounded-pill fw-semibold mb-3">
                <i class="bi bi-patch-check-fill me-1"></i> Jakarta EE 10 • Hibernate JPA • Filter Security
            </span>
            <h1 class="display-5 fw-bold text-dark mb-3">
                Hệ Thống Quản Lý Đa Lớp &amp; Phân Quyền Bảo Mật
            </h1>
            <p class="lead text-muted mb-4">
                Đồ án thực hành <strong>Lab 10</strong> - Công nghệ Java (IT3242). Tích hợp kiến trúc MVC Model 2, Hibernate JPA, Session Management, Filter bảo vệ URL và Role-based Access Control (RBAC).
            </p>
            <div class="d-flex flex-wrap gap-3">
                <c:choose>
                    <c:when test="${not empty sessionScope.currentUser}">
                        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary btn-lg px-4 py-2 rounded-pill shadow-sm">
                            <i class="bi bi-speedometer2 me-2"></i> Truy Cập Dashboard
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary btn-lg px-4 py-2 rounded-pill shadow-sm">
                            <i class="bi bi-box-arrow-in-right me-2"></i> Đăng Nhập Hệ Thống
                        </a>
                    </c:otherwise>
                </c:choose>
                <a href="#features" class="btn btn-outline-secondary btn-lg px-4 py-2 rounded-pill">
                    <i class="bi bi-info-circle me-2"></i> Tìm Hiểu Tính Năng
                </a>
            </div>
        </div>
        <div class="col-lg-5 text-center mt-4 mt-lg-0">
            <div class="card border-0 shadow-lg p-4 rounded-4 bg-white">
                <h5 class="fw-bold text-primary mb-3"><i class="bi bi-shield-check me-2"></i>Tài Khoản Thử Nghiệm Nhanh</h5>
                <div class="list-group list-group-flush text-start">
                    <div class="list-group-item px-0 py-2">
                        <div class="d-flex justify-content-between align-items-center">
                            <strong class="text-danger"><i class="bi bi-person-fill-gear me-1"></i> ADMIN</strong>
                            <span class="badge bg-danger">Toàn quyền hệ thống</span>
                        </div>
                        <small class="text-muted d-block">admin@eaut.edu.vn | Pass: 123456</small>
                    </div>
                    <div class="list-group-item px-0 py-2">
                        <div class="d-flex justify-content-between align-items-center">
                            <strong class="text-warning"><i class="bi bi-person-workspace me-1"></i> STAFF</strong>
                            <span class="badge bg-warning text-dark">Quản lý nghiệp vụ</span>
                        </div>
                        <small class="text-muted d-block">staff@eaut.edu.vn | Pass: 123456</small>
                    </div>
                    <div class="list-group-item px-0 py-2">
                        <div class="d-flex justify-content-between align-items-center">
                            <strong class="text-success"><i class="bi bi-person me-1"></i> USER</strong>
                            <span class="badge bg-success">Cá nhân &amp; Hồ sơ</span>
                        </div>
                        <small class="text-muted d-block">user@eaut.edu.vn | Pass: 123456</small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Feature Grid -->
    <div id="features" class="pt-4">
        <div class="text-center mb-5">
            <h2 class="fw-bold text-dark">Các Module Chức Năng Chính</h2>
            <p class="text-muted">Hoàn thiện theo các yêu cầu từ Bài 1 đến Bài 13 và Mục 7 tổng hợp</p>
        </div>

        <div class="row g-4">
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 border-0 shadow-sm rounded-4 p-3 text-center">
                    <div class="mb-3 text-primary fs-1">
                        <i class="bi bi-shield-lock"></i>
                    </div>
                    <h5 class="fw-bold">Login &amp; RBAC</h5>
                    <p class="text-muted small mb-0">
                        Xác thực người dùng từ DB, phân quyền vai trò ADMIN/STAFF/USER, bảo vệ URL bằng AuthenticationFilter và AuthorizationFilter.
                    </p>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 border-0 shadow-sm rounded-4 p-3 text-center">
                    <div class="mb-3 text-success fs-1">
                        <i class="bi bi-people"></i>
                    </div>
                    <h5 class="fw-bold">Quản Lý Sinh Viên</h5>
                    <p class="text-muted small mb-0">
                        CRUD thông tin sinh viên với JPA, tính xếp loại tự động theo GPA, validate mã SV, email, lớp học.
                    </p>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 border-0 shadow-sm rounded-4 p-3 text-center">
                    <div class="mb-3 text-warning fs-1">
                        <i class="bi bi-book"></i>
                    </div>
                    <h5 class="fw-bold">Quản Lý Thư Viện Sách</h5>
                    <p class="text-muted small mb-0">
                        Quản lý danh mục sách, tác giả, năm xuất bản, đơn giá, tồn kho với bộ lọc và tìm kiếm linh hoạt.
                    </p>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 border-0 shadow-sm rounded-4 p-3 text-center">
                    <div class="mb-3 text-info fs-1">
                        <i class="bi bi-box-seam"></i>
                    </div>
                    <h5 class="fw-bold">Quản Lý Sản Phẩm</h5>
                    <p class="text-muted small mb-0">
                        Quản lý kho sản phẩm, mã vạch, phân loại danh mục, cập nhật giá và tồn kho với Transaction an toàn.
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
