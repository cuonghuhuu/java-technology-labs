<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Đăng Nhập - Hệ Thống Lab 10 EAUT" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-5">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card border-0 shadow-lg rounded-4 overflow-hidden">
                <div class="bg-primary bg-gradient p-4 text-center text-white">
                    <i class="bi bi-shield-lock-fill display-4 mb-2"></i>
                    <h3 class="fw-bold mb-0">Đăng Nhập Hệ Thống</h3>
                    <p class="small text-white-50 mb-0">Học phần Công nghệ Java - Lab 10</p>
                </div>

                <div class="card-body p-4 p-md-5">
                    <!-- Error Alert -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-circle-fill me-2"></i>
                            <strong>Lỗi:</strong> ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Logout Success Alert -->
                    <c:if test="${param.logout == 'true'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="bi bi-check-circle-fill me-2"></i>
                            Bạn đã đăng xuất khỏi hệ thống thành công.
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Login Form -->
                    <form method="post" action="${pageContext.request.contextPath}/auth">
                        <c:if test="${not empty param.redirect}">
                            <input type="hidden" name="redirect" value="<c:out value='${param.redirect}'/>">
                        </c:if>
                        <c:if test="${not empty redirect}">
                            <input type="hidden" name="redirect" value="<c:out value='${redirect}'/>">
                        </c:if>

                        <div class="mb-3">
                            <label for="emailInput" class="form-label">
                                <i class="bi bi-envelope-fill text-primary me-1"></i> Địa chỉ Email
                            </label>
                            <input type="email" class="form-control form-control-lg rounded-3" id="emailInput"
                                   name="email" value="${inputEmail != null ? inputEmail : ''}"
                                   placeholder="vd: admin@eaut.edu.vn" required autofocus>
                        </div>

                        <div class="mb-4">
                            <label for="passwordInput" class="form-label">
                                <i class="bi bi-key-fill text-primary me-1"></i> Mật khẩu
                            </label>
                            <input type="password" class="form-control form-control-lg rounded-3" id="passwordInput"
                                   name="password" placeholder="Nhập mật khẩu..." required>
                        </div>

                        <button type="submit" class="btn btn-primary btn-lg w-100 rounded-pill shadow-sm mb-4">
                            <i class="bi bi-box-arrow-in-right me-2"></i> Đăng Nhập
                        </button>
                    </form>

                    <!-- Demo Quick Fill Buttons -->
                    <div class="border-top pt-3">
                        <small class="text-muted d-block text-center mb-2">Điền nhanh tài khoản kiểm thử:</small>
                        <div class="d-flex gap-2 justify-content-center">
                            <button type="button" class="btn btn-outline-danger btn-sm rounded-pill" onclick="fillCredentials('admin@eaut.edu.vn', '123456')">
                                <i class="bi bi-person-fill-gear"></i> Admin
                            </button>
                            <button type="button" class="btn btn-outline-warning btn-sm rounded-pill text-dark" onclick="fillCredentials('staff@eaut.edu.vn', '123456')">
                                <i class="bi bi-person-workspace"></i> Staff
                            </button>
                            <button type="button" class="btn btn-outline-success btn-sm rounded-pill" onclick="fillCredentials('user@eaut.edu.vn', '123456')">
                                <i class="bi bi-person"></i> User
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function fillCredentials(email, pass) {
        document.getElementById('emailInput').value = email;
        document.getElementById('passwordInput').value = pass;
    }
</script>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
