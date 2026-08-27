<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Bảng Điều Khiển (Dashboard) - Lab 10" />
<c:set var="activePage" value="dashboard" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <!-- Welcome Header -->
    <div class="row align-items-center mb-4 bg-white p-4 rounded-4 shadow-sm">
        <div class="col-md-8">
            <h2 class="fw-bold text-dark mb-1">
                Xin chào, <span class="text-primary">${sessionScope.currentUser.fullName}</span>!
            </h2>
            <p class="text-muted mb-0">
                Chào mừng bạn quay trở lại hệ thống quản lý đa lớp Jakarta EE 10.
            </p>
        </div>
        <div class="col-md-4 text-md-end mt-3 mt-md-0">
            <span class="badge fs-6 px-3 py-2 rounded-pill
                <c:choose>
                    <c:when test="${sessionScope.currentUser.role == 'ADMIN'}">bg-danger</c:when>
                    <c:when test="${sessionScope.currentUser.role == 'STAFF'}">bg-warning text-dark</c:when>
                    <c:otherwise>bg-success</c:otherwise>
                </c:choose>">
                <i class="bi bi-shield-lock me-1"></i> Vai trò: ${sessionScope.currentUser.role}
            </span>
        </div>
    </div>

    <!-- Statistics Cards -->
    <div class="row g-4 mb-4">
        <!-- Students Card -->
        <div class="col-sm-6 col-lg-3">
            <div class="card card-stat bg-white h-100 p-3">
                <div class="d-flex align-items-center justify-content-between">
                    <div>
                        <span class="text-muted small text-uppercase fw-bold">Sinh Viên</span>
                        <h3 class="fw-bold text-dark mb-0 mt-1">${totalStudents != null ? totalStudents : 0}</h3>
                    </div>
                    <div class="icon-box bg-primary-subtle text-primary">
                        <i class="bi bi-people-fill"></i>
                    </div>
                </div>
                <c:if test="${sessionScope.currentUser.role == 'ADMIN' || sessionScope.currentUser.role == 'STAFF'}">
                    <div class="mt-3 pt-2 border-top">
                        <a href="${pageContext.request.contextPath}/staff/sinh-vien" class="small text-primary text-decoration-none fw-semibold">
                            Xem danh sách <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Books Card -->
        <div class="col-sm-6 col-lg-3">
            <div class="card card-stat bg-white h-100 p-3">
                <div class="d-flex align-items-center justify-content-between">
                    <div>
                        <span class="text-muted small text-uppercase fw-bold">Sách Thư Viện</span>
                        <h3 class="fw-bold text-dark mb-0 mt-1">${totalBooks != null ? totalBooks : 0}</h3>
                    </div>
                    <div class="icon-box bg-success-subtle text-success">
                        <i class="bi bi-book-half"></i>
                    </div>
                </div>
                <c:if test="${sessionScope.currentUser.role == 'ADMIN' || sessionScope.currentUser.role == 'STAFF'}">
                    <div class="mt-3 pt-2 border-top">
                        <a href="${pageContext.request.contextPath}/staff/sach" class="small text-success text-decoration-none fw-semibold">
                            Xem danh sách <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Products Card -->
        <div class="col-sm-6 col-lg-3">
            <div class="card card-stat bg-white h-100 p-3">
                <div class="d-flex align-items-center justify-content-between">
                    <div>
                        <span class="text-muted small text-uppercase fw-bold">Sản Phẩm Kho</span>
                        <h3 class="fw-bold text-dark mb-0 mt-1">${totalProducts != null ? totalProducts : 0}</h3>
                    </div>
                    <div class="icon-box bg-warning-subtle text-warning">
                        <i class="bi bi-box-seam-fill"></i>
                    </div>
                </div>
                <c:if test="${sessionScope.currentUser.role == 'ADMIN' || sessionScope.currentUser.role == 'STAFF'}">
                    <div class="mt-3 pt-2 border-top">
                        <a href="${pageContext.request.contextPath}/staff/san-pham" class="small text-warning text-decoration-none fw-semibold">
                            Xem danh sách <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Users Card (Admin only or General) -->
        <div class="col-sm-6 col-lg-3">
            <div class="card card-stat bg-white h-100 p-3">
                <div class="d-flex align-items-center justify-content-between">
                    <div>
                        <span class="text-muted small text-uppercase fw-bold">Tài Khoản</span>
                        <h3 class="fw-bold text-dark mb-0 mt-1">${totalUsers != null ? totalUsers : 0}</h3>
                    </div>
                    <div class="icon-box bg-danger-subtle text-danger">
                        <i class="bi bi-person-badge-fill"></i>
                    </div>
                </div>
                <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                    <div class="mt-3 pt-2 border-top">
                        <a href="${pageContext.request.contextPath}/admin/users" class="small text-danger text-decoration-none fw-semibold">
                            Quản lý tài khoản <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- Quick Navigation & Information Grid -->
    <div class="row g-4">
        <!-- Quick Actions Column -->
        <div class="col-lg-5">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white h-100">
                <h5 class="fw-bold text-dark mb-3"><i class="bi bi-lightning-charge-fill text-warning me-2"></i>Thao Tác Nhanh</h5>
                
                <div class="d-grid gap-2">
                    <c:if test="${sessionScope.currentUser.role == 'ADMIN' || sessionScope.currentUser.role == 'STAFF'}">
                        <a href="${pageContext.request.contextPath}/staff/sinh-vien?action=new" class="btn btn-outline-primary text-start py-2">
                            <i class="bi bi-person-plus-fill me-2"></i> Thêm mới Sinh viên
                        </a>
                        <a href="${pageContext.request.contextPath}/staff/sach?action=new" class="btn btn-outline-success text-start py-2">
                            <i class="bi bi-journal-plus me-2"></i> Thêm mới Sách vào kho
                        </a>
                        <a href="${pageContext.request.contextPath}/staff/san-pham?action=new" class="btn btn-outline-warning text-dark text-start py-2">
                            <i class="bi bi-bag-plus-fill me-2"></i> Thêm mới Sản phẩm
                        </a>
                    </c:if>

                    <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/admin/users?action=new" class="btn btn-outline-danger text-start py-2">
                            <i class="bi bi-person-gear me-2"></i> Tạo tài khoản người dùng mới
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/logs" class="btn btn-outline-info text-dark text-start py-2">
                            <i class="bi bi-clock-history me-2"></i> Kiểm tra Nhật ký hoạt động
                        </a>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-light text-start py-2">
                        <i class="bi bi-person-lines-fill me-2"></i> Cập nhật thông tin cá nhân
                    </a>
                    <a href="${pageContext.request.contextPath}/user/change-password" class="btn btn-light text-start py-2">
                        <i class="bi bi-key-fill me-2"></i> Đổi mật khẩu tài khoản
                    </a>
                </div>
            </div>
        </div>

        <!-- Recent Activity Logs or System Info Column -->
        <div class="col-lg-7">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white h-100">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="fw-bold text-dark mb-0"><i class="bi bi-activity text-primary me-2"></i>Nhật Ký Hoạt Động Gần Đây</h5>
                    <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/admin/logs" class="small text-decoration-none">Xem tất cả</a>
                    </c:if>
                </div>

                <div class="table-responsive">
                    <table class="table table-sm table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Thời gian</th>
                                <th>Người dùng</th>
                                <th>Hành động</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="log" items="${recentLogs}">
                                <tr>
                                    <td class="small text-muted">${log.timestamp}</td>
                                    <td class="small fw-semibold text-truncate" style="max-width: 140px;">${log.userEmail}</td>
                                    <td><span class="badge bg-secondary-subtle text-secondary small">${log.action}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${log.status == 'SUCCESS'}">
                                                <span class="badge bg-success-subtle text-success small">Thành công</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger-subtle text-danger small">Thất bại</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty recentLogs}">
                                <tr>
                                    <td colspan="4" class="text-center text-muted py-3">Chưa có nhật ký nào được ghi nhận.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
