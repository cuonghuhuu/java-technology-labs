<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Nhật Ký Hoạt Động (Audit Logs) - Admin" />
<c:set var="activeGroup" value="admin" />
<c:set var="activePage" value="logs" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <!-- Title -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-1">
                <i class="bi bi-clock-history text-info me-2"></i>Nhật Ký Hoạt Động Hệ Thống (Audit Logs)
            </h3>
            <p class="text-muted small mb-0">Theo dõi toàn bộ lịch sử đăng nhập, đăng xuất, thêm/sửa/xóa và các thao tác nghiệp vụ</p>
        </div>
    </div>

    <!-- Search Card -->
    <div class="card border-0 shadow-sm rounded-4 p-3 mb-4 bg-white">
        <form method="get" action="${pageContext.request.contextPath}/admin/logs" class="row g-3 align-items-center">
            <div class="col-md-8 col-lg-6">
                <div class="input-group">
                    <span class="input-group-text bg-light border-end-0"><i class="bi bi-search text-muted"></i></span>
                    <input type="text" name="keyword" class="form-control bg-light border-start-0"
                           placeholder="Tìm kiếm theo email người dùng, hành động, chi tiết..." value="<c:out value='${keyword}'/>">
                </div>
            </div>
            <div class="col-md-4 col-lg-2">
                <button type="submit" class="btn btn-primary w-100 rounded-pill">
                    <i class="bi bi-funnel me-1"></i> Tìm kiếm
                </button>
            </div>
            <c:if test="${not empty keyword}">
                <div class="col-md-4 col-lg-2">
                    <a href="${pageContext.request.contextPath}/admin/logs" class="btn btn-outline-secondary w-100 rounded-pill">
                        <i class="bi bi-x-circle me-1"></i> Xóa tìm kiếm
                    </a>
                </div>
            </c:if>
        </form>
    </div>

    <!-- Log Table Card -->
    <div class="card border-0 shadow-sm rounded-4 overflow-hidden bg-white">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Thời Gian</th>
                        <th>Tài Khoản</th>
                        <th>Vai Trò</th>
                        <th>Hành Động (Action)</th>
                        <th>Chi Tiết</th>
                        <th>Địa Chỉ IP</th>
                        <th class="text-end pe-4">Trạng Thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="l" items="${logList}">
                        <tr>
                            <td class="ps-4 fw-bold text-secondary">#${l.id}</td>
                            <td class="small text-muted text-nowrap">${l.timestamp}</td>
                            <td><code class="fw-semibold">${l.userEmail}</code></td>
                            <td>
                                <span class="badge rounded-pill
                                    <c:choose>
                                        <c:when test="${l.userRole == 'ADMIN'}">bg-danger</c:when>
                                        <c:when test="${l.userRole == 'STAFF'}">bg-warning text-dark</c:when>
                                        <c:when test="${l.userRole == 'USER'}">bg-success</c:when>
                                        <c:otherwise>bg-secondary</c:otherwise>
                                    </c:choose>">
                                    ${l.userRole}
                                </span>
                            </td>
                            <td>
                                <span class="badge bg-primary-subtle text-primary border border-primary-subtle">
                                    ${l.action}
                                </span>
                            </td>
                            <td class="small text-muted">${l.details}</td>
                            <td class="small text-secondary"><code>${l.ipAddress}</code></td>
                            <td class="text-end pe-4">
                                <c:choose>
                                    <c:when test="${l.status == 'SUCCESS'}">
                                        <span class="badge bg-success-subtle text-success">
                                            <i class="bi bi-check-circle-fill me-1"></i> SUCCESS
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger-subtle text-danger">
                                            <i class="bi bi-x-circle-fill me-1"></i> FAILED
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty logList}">
                        <tr>
                            <td colspan="8" class="text-center py-4 text-muted">
                                <i class="bi bi-inbox fs-2 d-block mb-2"></i>
                                Không có dữ liệu nhật ký nào.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
