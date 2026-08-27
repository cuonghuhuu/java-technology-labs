<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="403 - Không có quyền truy cập" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-5">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6 text-center">
            <div class="card border-0 shadow-lg p-4 p-md-5 rounded-4">
                <div class="mb-4">
                    <span class="display-1 text-danger fw-bold"><i class="bi bi-shield-x"></i> 403</span>
                </div>
                <h3 class="fw-bold text-dark mb-2">Truy Cập Bị Từ Chối (Forbidden)</h3>
                <p class="text-muted mb-4">
                    Tài khoản của bạn 
                    <c:if test="${not empty sessionScope.currentUser}">
                        (vai trò: <span class="badge bg-danger">${sessionScope.currentUser.role}</span>)
                    </c:if>
                    không có quyền truy cập vào đường dẫn hoặc chức năng được yêu cầu.
                </p>

                <div class="alert alert-warning text-start small mb-4">
                    <i class="bi bi-exclamation-triangle-fill me-1"></i>
                    <strong>Gợi ý:</strong> 
                    <ul class="mb-0 mt-1 ps-3">
                        <li>Đường dẫn <code>/admin/*</code> chỉ dành cho vai trò <strong>ADMIN</strong>.</li>
                        <li>Đường dẫn <code>/staff/*</code> chỉ dành cho vai trò <strong>ADMIN</strong> hoặc <strong>STAFF</strong>.</li>
                        <li>Nếu bạn cần cấp quyền, vui lòng liên hệ Quản trị viên hệ thống.</li>
                    </ul>
                </div>

                <div class="d-flex justify-content-center gap-3">
                    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary px-4 py-2 rounded-pill">
                        <i class="bi bi-house-door me-1"></i> Về Trang Dashboard
                    </a>
                    <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-outline-danger px-4 py-2 rounded-pill">
                        <i class="bi bi-box-arrow-right me-1"></i> Đăng nhập tài khoản khác
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
