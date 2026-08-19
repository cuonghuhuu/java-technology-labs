<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         isELIgnored="false" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Đăng nhập - Lab 7</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<header class="topbar">

    <div class="topbar-inner">

        <a class="brand"
           href="${pageContext.request.contextPath}/">
            Java Lab 7
        </a>

    </div>

</header>


<main class="login-page">

    <section class="login-card">

        <div class="login-logo">
            J7
        </div>

        <p class="eyebrow">
            CÔNG NGHỆ JAVA
        </p>

        <h1>Đăng nhập</h1>

        <p class="subtitle login-subtitle">
            Đăng nhập để truy cập hệ thống quản lý Lab 7
        </p>


        <c:if test="${not empty error}">

            <div class="alert alert-danger">
                ⚠ ${error}
            </div>

        </c:if>


        <form method="post"
              action="${pageContext.request.contextPath}/login">

            <div class="form-group">

                <label for="username">
                    Tên đăng nhập
                </label>

                <input id="username"
                       name="username"
                       type="text"
                       placeholder="Nhập tên đăng nhập"
                       autocomplete="username"
                       required>

            </div>


            <div class="form-group">

                <label for="password">
                    Mật khẩu
                </label>

                <input id="password"
                       name="password"
                       type="password"
                       placeholder="Nhập mật khẩu"
                       autocomplete="current-password"
                       required>

            </div>


            <button class="btn btn-primary login-button"
                    type="submit">

                Đăng nhập

            </button>

        </form>


        <div class="demo-account">

            <span>Tài khoản demo</span>

            <strong>
                admin / 123456
            </strong>

        </div>


        <div class="login-footer">

            <a href="${pageContext.request.contextPath}/">
                ← Quay lại trang chủ
            </a>

        </div>

    </section>

</main>

</body>
</html>