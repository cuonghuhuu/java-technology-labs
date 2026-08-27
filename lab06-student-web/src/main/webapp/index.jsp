<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lab 6 - Quản lý sinh viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="container narrow hero">
    <section class="panel">
        <p class="eyebrow">Công nghệ Java · IT3242</p>
        <h1>LAB 6 - QUẢN LÝ SINH VIÊN</h1>
        <p>Servlet · JSP · JSTL · Filter · Listener</p>
        <div class="actions">
            <a class="button" href="${pageContext.request.contextPath}/hello">Hello Servlet</a>
            <a class="button secondary" href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
        </div>
        <c:if test="${not empty sessionScope.username}">
            <p><a href="${pageContext.request.contextPath}/dashboard">Mở Dashboard</a> · <a href="${pageContext.request.contextPath}/students">Danh sách sinh viên</a></p>
        </c:if>
    </section>
</main>
</body>
</html>
