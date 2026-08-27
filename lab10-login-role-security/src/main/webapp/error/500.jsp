<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="500 - Lỗi hệ thống" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-7 text-center">
            <div class="card border-0 shadow-lg p-4 p-md-5 rounded-4">
                <div class="mb-4">
                    <span class="display-1 text-danger fw-bold"><i class="bi bi-exclamation-octagon"></i> 500</span>
                </div>
                <h3 class="fw-bold text-dark mb-2">Đã Xảy Ra Lỗi Hệ Thống</h3>
                <p class="text-muted mb-4">
                    Máy chủ gặp sự cố trong quá trình xử lý yêu cầu của bạn. Vui lòng thử lại sau giây lát.
                </p>

                <c:if test="${not empty pageContext.exception}">
                    <div class="alert alert-danger text-start small mb-4 overflow-auto" style="max-height: 200px;">
                        <strong>Chi tiết lỗi:</strong> ${pageContext.exception.message}
                    </div>
                </c:if>

                <div class="d-flex justify-content-center gap-3">
                    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary px-4 py-2 rounded-pill">
                        <i class="bi bi-house-door me-1"></i> Về Trang Dashboard
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
