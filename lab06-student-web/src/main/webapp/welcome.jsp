<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Chào mừng</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body><main class="container narrow"><section class="panel"><h1>Chào mừng ${sessionScope.username}</h1>
<p>Bạn đã đăng nhập với vai trò <strong>${sessionScope.role}</strong>.</p>
<p><a class="button" href="${pageContext.request.contextPath}/dashboard">Đến Dashboard</a> <a class="button secondary" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></p>
</section></main></body>
</html>
