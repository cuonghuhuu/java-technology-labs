<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Dashboard - Lab 6</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body><main class="container">
<nav class="navbar"><a href="${pageContext.request.contextPath}/">Trang chủ</a><a href="${pageContext.request.contextPath}/students">Quản lý sinh viên</a><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></nav>
<section class="panel"><p class="eyebrow">Dashboard</p><h1>Xin chào, ${sessionScope.username}</h1>
<div class="cards"><article class="card"><span>Vai trò</span><strong>${sessionScope.role}</strong></article><article class="card"><span>Tổng số sinh viên</span><strong>${studentCount}</strong></article><article class="card"><span>Thời gian đăng nhập</span><strong>${sessionScope.loginTime}</strong></article></div>
<h2>Số sinh viên theo lớp</h2><c:choose><c:when test="${empty classCounts}"><p class="muted">Chưa có dữ liệu.</p></c:when><c:otherwise><div class="table-wrap"><table><thead><tr><th>Lớp</th><th>Số sinh viên</th></tr></thead><tbody><c:forEach items="${classCounts}" var="entry"><tr><td>${entry.key}</td><td>${entry.value}</td></tr></c:forEach></tbody></table></div></c:otherwise></c:choose>
</section></main></body>
</html>
