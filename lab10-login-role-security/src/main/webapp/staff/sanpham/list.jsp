<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Quản Lý Sản Phẩm - Lab 10" />
<c:set var="activeGroup" value="business" />
<c:set var="activePage" value="sanpham" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <!-- Header -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4 gap-3">
        <div>
            <h3 class="fw-bold text-dark mb-1">
                <i class="bi bi-box-seam-fill text-warning me-2"></i>Quản Lý Kho Sản Phẩm (Module 3 - JPA)
            </h3>
            <p class="text-muted small mb-0">Quản lý danh mục hàng hóa, giá bán, số lượng tồn kho và mô tả kỹ thuật</p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/staff/san-pham?action=new" class="btn btn-warning text-dark px-3 py-2 rounded-pill shadow-sm">
                <i class="bi bi-bag-plus-fill me-1"></i> Thêm Sản Phẩm Mới
            </a>
        </div>
    </div>

    <!-- Alert Notifications -->
    <c:if test="${param.msg == 'create_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Thêm mới sản phẩm thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'update_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Cập nhật thông tin sản phẩm thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'delete_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Đã xóa sản phẩm khỏi kho!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Search Card -->
    <div class="card border-0 shadow-sm rounded-4 p-3 mb-4 bg-white">
        <form method="get" action="${pageContext.request.contextPath}/staff/san-pham" class="row g-3 align-items-center">
            <div class="col-md-8 col-lg-6">
                <div class="input-group">
                    <span class="input-group-text bg-light border-end-0"><i class="bi bi-search text-muted"></i></span>
                    <input type="text" name="keyword" class="form-control bg-light border-start-0"
                           placeholder="Tìm kiếm theo mã SP, tên sản phẩm, danh mục..." value="<c:out value='${keyword}'/>">
                </div>
            </div>
            <div class="col-md-4 col-lg-2">
                <button type="submit" class="btn btn-warning text-dark w-100 rounded-pill">
                    <i class="bi bi-search me-1"></i> Tìm kiếm
                </button>
            </div>
            <c:if test="${not empty keyword}">
                <div class="col-md-4 col-lg-2">
                    <a href="${pageContext.request.contextPath}/staff/san-pham" class="btn btn-outline-secondary w-100 rounded-pill">
                        <i class="bi bi-x-circle me-1"></i> Xóa tìm kiếm
                    </a>
                </div>
            </c:if>
        </form>
    </div>

    <!-- Product Table -->
    <div class="card border-0 shadow-sm rounded-4 overflow-hidden bg-white">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Mã SP</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Danh Mục</th>
                        <th>Đơn Giá</th>
                        <th>Tồn Kho</th>
                        <th>Mô Tả</th>
                        <th class="text-end pe-4">Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${sanPhamList}">
                        <tr>
                            <td class="ps-4 fw-bold text-secondary">#${p.id}</td>
                            <td><span class="badge bg-light text-dark border"><code>${p.maSanPham}</code></span></td>
                            <td class="fw-semibold text-dark">${p.tenSanPham}</td>
                            <td><span class="badge bg-primary-subtle text-primary">${p.danhMuc}</span></td>
                            <td>
                                <strong class="text-danger">
                                    <fmt:formatNumber value="${p.gia}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </strong>
                            </td>
                            <td>
                                <span class="badge ${p.soLuong > 5 ? 'bg-success-subtle text-success' : 'bg-danger-subtle text-danger'}">
                                    ${p.soLuong} cái
                                </span>
                            </td>
                            <td><small class="text-muted text-truncate d-inline-block" style="max-width: 220px;">${p.moTa}</small></td>
                            <td class="text-end pe-4">
                                <div class="btn-group btn-group-sm">
                                    <a href="${pageContext.request.contextPath}/staff/san-pham?action=edit&id=${p.id}"
                                       class="btn btn-outline-primary" title="Chỉnh sửa">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/staff/san-pham?action=delete&id=${p.id}"
                                       class="btn btn-outline-danger" title="Xóa"
                                       onclick="return confirmDelete(event, 'Bạn có chắc chắn muốn xóa sản phẩm [${p.tenSanPham}] không?')">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty sanPhamList}">
                        <tr>
                            <td colspan="8" class="text-center py-4 text-muted">
                                <i class="bi bi-inbox fs-2 d-block mb-2"></i>
                                Chưa có sản phẩm nào trong kho.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
