<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${formAction == 'update' ? 'Cập nhật' : 'Thêm'} sinh viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="container narrow">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/students">Danh sách sinh viên</a>
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </nav>
    <section class="panel">
        <h1><c:choose><c:when test="${formAction == 'update'}">Cập nhật sinh viên</c:when><c:otherwise>Thêm sinh viên</c:otherwise></c:choose></h1>
        <c:if test="${not empty error}"><p class="alert error">${error}</p></c:if>
        <form method="post" action="${pageContext.request.contextPath}/students" class="form-grid">
            <input type="hidden" name="action" value="${formAction}">
            <label>Mã sinh viên
                <input name="id" value="${student.id}" required ${formAction == 'update' ? 'readonly' : ''}>
            </label>
            <label>Họ tên
                <input name="name" value="${student.name}" required>
            </label>
            <label>Lớp
                <input name="className" value="${student.className}" required>
            </label>
            <label>Email
                <input type="email" name="email" value="${student.email}" required>
            </label>
            <div class="actions">
                <button type="submit">Lưu</button>
                <a class="button secondary" href="${pageContext.request.contextPath}/students">Hủy</a>
            </div>
        </form>
    </section>
</main>
</body>
</html>
