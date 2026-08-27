<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách sinh viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="container">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <span>Xin chào, ${sessionScope.username} (${sessionScope.role})</span>
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </nav>
    <section class="panel">
        <div class="heading-row">
            <h1>Quản lý sinh viên</h1>
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <a class="button" href="${pageContext.request.contextPath}/students?action=new">Thêm sinh viên</a>
            </c:if>
        </div>
        <form method="get" action="${pageContext.request.contextPath}/students" class="search-form">
            <input type="search" name="keyword" value="${keyword}" placeholder="Nhập họ tên cần tìm">
            <button type="submit">Tìm kiếm</button>
            <a class="button secondary" href="${pageContext.request.contextPath}/students">Xóa tìm kiếm</a>
        </form>
        <c:choose>
            <c:when test="${empty students}">
                <p class="alert info">Không tìm thấy sinh viên phù hợp.</p>
            </c:when>
            <c:otherwise>
                <div class="table-wrap"><table>
                    <thead><tr><th>Mã SV</th><th>Họ tên</th><th>Lớp</th><th>Email</th><th>Thao tác</th></tr></thead>
                    <tbody>
                    <c:forEach items="${students}" var="student">
                        <tr>
                            <td>${student.id}</td><td>${student.name}</td><td>${student.className}</td><td>${student.email}</td>
                            <td>
                                <c:if test="${sessionScope.role == 'ADMIN'}">
                                    <a class="link-button" href="${pageContext.request.contextPath}/students?action=edit&amp;id=${student.id}">Sửa</a>
                                    <a class="link-button danger" href="${pageContext.request.contextPath}/students?action=delete&amp;id=${student.id}" onclick="return confirm('Bạn có chắc muốn xóa sinh viên này?')">Xóa</a>
                                </c:if>
                                <c:if test="${sessionScope.role != 'ADMIN'}"><span class="muted">Chỉ xem</span></c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table></div>
            </c:otherwise>
        </c:choose>
    </section>
</main>
</body>
</html>
