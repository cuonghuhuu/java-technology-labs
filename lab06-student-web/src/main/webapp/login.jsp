<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Đăng nhập - Lab 6</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body><main class="container narrow"><section class="panel">
    <h1>Đăng nhập</h1><p class="muted">Quản lý sinh viên Lab 6</p>
    <c:if test="${not empty error}"><p class="alert error">${error}</p></c:if>
    <form method="post" action="${pageContext.request.contextPath}/login" class="form-grid">
        <label>Tên đăng nhập<input name="username" required autocomplete="username"></label>
        <label>Mật khẩu<input type="password" name="password" required autocomplete="current-password"></label>
        <button type="submit">Đăng nhập</button>
    </form>
    <p class="muted">Demo: admin / 123456 hoặc user / 123456</p><a href="${pageContext.request.contextPath}/">← Trang chủ</a>
</section></main></body>
</html>
