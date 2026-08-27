<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Đổi Mật Khẩu - Lab 10" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-5">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <div class="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                    <div class="bg-warning-subtle text-warning rounded-circle p-3 fs-3">
                        <i class="bi bi-shield-lock-fill"></i>
                    </div>
                    <div>
                        <h4 class="fw-bold mb-0">Đổi Mật Khẩu</h4>
                        <p class="text-muted small mb-0">Nâng cao tính bảo mật cho tài khoản của bạn</p>
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

                <form method="post" action="${pageContext.request.contextPath}/user/change-password">
                    <div class="mb-3">
                        <label for="oldPassword" class="form-label">Mật khẩu hiện tại <span class="text-danger">*</span></label>
                        <input type="password" class="form-control" id="oldPassword" name="oldPassword"
                               placeholder="Nhập mật khẩu hiện tại" required>
                    </div>

                    <div class="mb-3">
                        <label for="newPassword" class="form-label">Mật khẩu mới <span class="text-danger">*</span></label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                               placeholder="Tối thiểu 6 ký tự" required minlength="6">
                    </div>

                    <div class="mb-4">
                        <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới <span class="text-danger">*</span></label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                               placeholder="Nhập lại mật khẩu mới" required minlength="6">
                    </div>

                    <div class="d-flex gap-2 justify-content-end">
                        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-secondary px-4 rounded-pill">
                            Quay lại
                        </a>
                        <button type="submit" class="btn btn-primary px-4 rounded-pill shadow-sm">
                            <i class="bi bi-key-fill me-1"></i> Cập Nhật Mật Khẩu
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
