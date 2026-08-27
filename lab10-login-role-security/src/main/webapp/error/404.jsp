<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="404 - Trang không tồn tại" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-5">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6 text-center">
            <div class="card border-0 shadow-lg p-4 p-md-5 rounded-4">
                <div class="mb-4">
                    <span class="display-1 text-warning fw-bold"><i class="bi bi-question-circle"></i> 404</span>
                </div>
                <h3 class="fw-bold text-dark mb-2">Trang Không Tồn Tại (Not Found)</h3>
                <p class="text-muted mb-4">
                    Đường dẫn bạn yêu cầu không tồn tại hoặc đã bị di chuyển sang địa chỉ khác.
                </p>

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
