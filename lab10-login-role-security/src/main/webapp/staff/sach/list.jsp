<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Quản Lý Thư Viện Sách - Lab 10" />
<c:set var="activeGroup" value="business" />
<c:set var="activePage" value="sach" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <!-- Header -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4 gap-3">
        <div>
            <h3 class="fw-bold text-dark mb-1">
                <i class="bi bi-book-half text-success me-2"></i>Quản Lý Thư Viện Sách (Module 2 - JPA)
            </h3>
            <p class="text-muted small mb-0">Quản lý kho sách, tác giả, thể loại, đơn giá và số lượng lưu hành</p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/staff/sach?action=new" class="btn btn-success px-3 py-2 rounded-pill shadow-sm">
                <i class="bi bi-journal-plus me-1"></i> Thêm Sách Mới
            </a>
        </div>
    </div>

    <!-- Alert Notifications -->
    <c:if test="${param.msg == 'create_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Thêm mới sách thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'update_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Cập nhật thông tin sách thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'delete_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Đã xóa sách khỏi danh mục!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Search Card -->
    <div class="card border-0 shadow-sm rounded-4 p-3 mb-4 bg-white">
        <form method="get" action="${pageContext.request.contextPath}/staff/sach" class="row g-3 align-items-center">
            <div class="col-md-8 col-lg-6">
                <div class="input-group">
                    <span class="input-group-text bg-light border-end-0"><i class="bi bi-search text-muted"></i></span>
                    <input type="text" name="keyword" class="form-control bg-light border-start-0"
                           placeholder="Tìm kiếm theo mã sách, tên sách, tác giả, thể loại..." value="<c:out value='${keyword}'/>">
                </div>
            </div>
            <div class="col-md-4 col-lg-2">
                <button type="submit" class="btn btn-success w-100 rounded-pill">
                    <i class="bi bi-search me-1"></i> Tìm kiếm
                </button>
            </div>
            <c:if test="${not empty keyword}">
                <div class="col-md-4 col-lg-2">
                    <a href="${pageContext.request.contextPath}/staff/sach" class="btn btn-outline-secondary w-100 rounded-pill">
                        <i class="bi bi-x-circle me-1"></i> Xóa tìm kiếm
                    </a>
                </div>
            </c:if>
        </form>
    </div>

    <!-- Book Table -->
    <div class="card border-0 shadow-sm rounded-4 overflow-hidden bg-white">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Mã Sách</th>
                        <th>Tên Sách</th>
                        <th>Tác Giả</th>
                        <th>Thể Loại</th>
                        <th>Năm XB</th>
                        <th>Đơn Giá</th>
                        <th>Số Lượng</th>
                        <th class="text-end pe-4">Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${sachList}">
                        <tr>
                            <td class="ps-4 fw-bold text-secondary">#${b.id}</td>
                            <td><span class="badge bg-light text-dark border"><code>${b.maSach}</code></span></td>
                            <td class="fw-semibold text-dark">${b.tenSach}</td>
                            <td><small class="text-muted">${b.tacGia}</small></td>
                            <td><span class="badge bg-info-subtle text-info">${b.theLoai}</span></td>
                            <td>${b.namXuatBan}</td>
                            <td>
                                <strong class="text-danger">
                                    <fmt:formatNumber value="${b.gia}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </strong>
                            </td>
                            <td>
                                <span class="badge ${b.soLuong > 10 ? 'bg-success-subtle text-success' : 'bg-warning-subtle text-warning'}">
                                    ${b.soLuong} cuốn
                                </span>
                            </td>
                            <td class="text-end pe-4">
                                <div class="btn-group btn-group-sm">
                                    <a href="${pageContext.request.contextPath}/staff/sach?action=edit&id=${b.id}"
                                       class="btn btn-outline-primary" title="Chỉnh sửa">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/staff/sach?action=delete&id=${b.id}"
                                       class="btn btn-outline-danger" title="Xóa"
                                       onclick="return confirmDelete(event, 'Bạn có chắc chắn muốn xóa sách [${b.tenSach}] không?')">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty sachList}">
                        <tr>
                            <td colspan="9" class="text-center py-4 text-muted">
                                <i class="bi bi-inbox fs-2 d-block mb-2"></i>
                                Chưa có sách nào trong danh mục.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
