<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Hồ Sơ Cá Nhân - Lab 10" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <div class="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                    <div class="bg-primary-subtle text-primary rounded-circle p-3 fs-3">
                        <i class="bi bi-person-fill"></i>
                    </div>
                    <div>
                        <h4 class="fw-bold mb-0">Hồ Sơ Cá Nhân</h4>
                        <p class="text-muted small mb-0">Quản lý và cập nhật thông tin tài khoản của bạn</p>
                    </div>
                </div>

                <!-- Alert Messages -->
                <c:if test="${not empty success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle-fill me-2"></i> ${success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-circle-fill me-2"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/user/profile">
                    <div class="mb-3">
                        <label class="form-label text-muted small text-uppercase">Mã ID Tài Khoản</label>
                        <input type="text" class="form-control bg-light" value="${user.id}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label text-muted small text-uppercase">Vai Trò Hệ Thống</label>
                        <div>
                            <span class="badge fs-6 px-3 py-2
                                <c:choose>
                                    <c:when test="${user.role == 'ADMIN'}">bg-danger</c:when>
                                    <c:when test="${user.role == 'STAFF'}">bg-warning text-dark</c:when>
                                    <c:otherwise>bg-success</c:otherwise>
                                </c:choose>">
                                ${user.role} (${user.role.displayName})
                            </span>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="fullName" class="form-label">Họ và Tên <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="fullName" name="fullName"
                               value="${user.fullName}" required>
                    </div>

                    <div class="mb-4">
                        <label for="email" class="form-label">Địa chỉ Email <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="email" name="email"
                               value="${user.email}" required>
                    </div>

                    <div class="d-flex gap-2 justify-content-end">
                        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-secondary px-4 rounded-pill">
                            Quay lại
                        </a>
                        <button type="submit" class="btn btn-primary px-4 rounded-pill shadow-sm">
                            <i class="bi bi-floppy me-1"></i> Lưu Thay Đổi
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
