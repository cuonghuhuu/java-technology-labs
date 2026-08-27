<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Quản Lý Tài Khoản Người Dùng - Admin" />
<c:set var="activeGroup" value="admin" />
<c:set var="activePage" value="users" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <!-- Header Title & Action -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4 gap-3">
        <div>
            <h3 class="fw-bold text-dark mb-1">
                <i class="bi bi-people-fill text-danger me-2"></i>Quản Lý Người Dùng &amp; Phân Quyền
            </h3>
            <p class="text-muted small mb-0">Quản lý danh sách tài khoản, phân bổ vai trò ADMIN/STAFF/USER và trạng thái khóa/mở</p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/users?action=new" class="btn btn-danger px-3 py-2 rounded-pill shadow-sm">
                <i class="bi bi-person-plus-fill me-1"></i> Thêm Người Dùng Mới
            </a>
        </div>
    </div>

    <!-- Alert Notifications -->
    <c:if test="${param.msg == 'create_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Thêm mới tài khoản người dùng thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'update_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Cập nhật thông tin tài khoản thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'delete_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Đã xóa tài khoản khỏi hệ thống!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'status_toggled'}">
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            <i class="bi bi-info-circle-fill me-2"></i> Đã thay đổi trạng thái hoạt động của tài khoản!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'reset_pass_success'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Đặt lại mật khẩu thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.error == 'self_delete_forbidden'}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i> Bạn không thể tự xóa tài khoản đang đăng nhập của chính mình!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.error == 'self_lock_forbidden'}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i> Bạn không thể tự khóa tài khoản đang đăng nhập của chính mình!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Search & Filter Card -->
    <div class="card border-0 shadow-sm rounded-4 p-3 mb-4 bg-white">
        <form method="get" action="${pageContext.request.contextPath}/admin/users" class="row g-3 align-items-center">
            <div class="col-md-6 col-lg-5">
                <div class="input-group">
                    <span class="input-group-text bg-light border-end-0"><i class="bi bi-search text-muted"></i></span>
                    <input type="text" name="keyword" class="form-control bg-light border-start-0"
                           placeholder="Tìm kiếm theo email, họ tên..." value="<c:out value='${keyword}'/>">
                </div>
            </div>
            <div class="col-md-4 col-lg-3">
                <select name="role" class="form-select bg-light">
                    <option value="">-- Tất cả vai trò --</option>
                    <c:forEach var="r" items="${roles}">
                        <option value="${r}" ${selectedRole == r.name() ? 'selected' : ''}>${r} (${r.displayName})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2 col-lg-2">
                <button type="submit" class="btn btn-primary w-100 rounded-pill">
                    <i class="bi bi-funnel me-1"></i> Lọc dữ liệu
                </button>
            </div>
            <c:if test="${not empty keyword || not empty selectedRole}">
                <div class="col-md-12 col-lg-2">
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary w-100 rounded-pill">
                        <i class="bi bi-x-circle me-1"></i> Xóa lọc
                    </a>
                </div>
            </c:if>
        </form>
    </div>

    <!-- User Table Card -->
    <div class="card border-0 shadow-sm rounded-4 overflow-hidden bg-white">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Họ và Tên</th>
                        <th>Email Đăng Nhập</th>
                        <th>Vai Trò (Role)</th>
                        <th>Trạng Thái</th>
                        <th>Ngày Tạo</th>
                        <th class="text-end pe-4">Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${userList}">
                        <tr>
                            <td class="ps-4 fw-bold text-secondary">#${u.id}</td>
                            <td>
                                <div class="fw-semibold text-dark">${u.fullName}</div>
                                <c:if test="${u.id == sessionScope.currentUser.id}">
                                    <span class="badge bg-info-subtle text-info small">Đang đăng nhập</span>
                                </c:if>
                            </td>
                            <td><code>${u.email}</code></td>
                            <td>
                                <span class="badge rounded-pill
                                    <c:choose>
                                        <c:when test="${u.role == 'ADMIN'}">bg-danger</c:when>
                                        <c:when test="${u.role == 'STAFF'}">bg-warning text-dark</c:when>
                                        <c:otherwise>bg-success</c:otherwise>
                                    </c:choose>">
                                    ${u.role}
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.active}">
                                        <span class="badge bg-success-subtle text-success">
                                            <i class="bi bi-check-circle me-1"></i> Hoạt động
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger-subtle text-danger">
                                            <i class="bi bi-lock-fill me-1"></i> Đã khóa
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="small text-muted">${u.createdAt}</td>
                            <td class="text-end pe-4">
                                <div class="btn-group btn-group-sm">
                                    <!-- Toggle status button -->
                                    <c:if test="${u.id != sessionScope.currentUser.id}">
                                        <a href="${pageContext.request.contextPath}/admin/users?action=toggle-status&id=${u.id}"
                                           class="btn ${u.active ? 'btn-outline-warning' : 'btn-outline-success'}"
                                           title="${u.active ? 'Khóa tài khoản' : 'Kích hoạt tài khoản'}">
                                            <i class="bi ${u.active ? 'bi-lock' : 'bi-unlock'}"></i>
                                        </a>
                                    </c:if>

                                    <!-- Edit Button -->
                                    <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}"
                                       class="btn btn-outline-primary" title="Chỉnh sửa">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>

                                    <!-- Reset Password Button -->
                                    <button type="button" class="btn btn-outline-info" title="Đặt lại mật khẩu"
                                            onclick="openResetModal('${u.id}', '${u.email}')">
                                        <i class="bi bi-key"></i>
                                    </button>

                                    <!-- Delete Button -->
                                    <c:if test="${u.id != sessionScope.currentUser.id}">
                                        <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.id}"
                                           class="btn btn-outline-danger" title="Xóa tài khoản"
                                           onclick="return confirmDelete(event, 'Bạn có chắc chắn muốn xóa tài khoản [${u.email}] không?')">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty userList}">
                        <tr>
                            <td colspan="7" class="text-center py-4 text-muted">
                                <i class="bi bi-inbox fs-2 d-block mb-2"></i>
                                Không tìm thấy người dùng nào phù hợp.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Reset Password -->
<div class="modal fade" id="resetPasswordModal" tabindex="-1" aria-labelledby="resetPasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form method="post" action="${pageContext.request.contextPath}/admin/users?action=reset-password">
            <div class="modal-content border-0 shadow rounded-4">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold" id="resetPasswordModalLabel">
                        <i class="bi bi-key-fill text-warning me-2"></i>Đặt Lại Mật Khẩu
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="id" id="resetUserId">
                    <p class="text-muted small">Đặt mật khẩu mới cho tài khoản: <strong id="resetUserEmail" class="text-dark"></strong></p>
                    <div class="mb-3">
                        <label for="newPasswordInput" class="form-label">Mật khẩu mới (tối thiểu 6 ký tự):</label>
                        <input type="password" name="newPassword" id="newPasswordInput" class="form-control"
                               placeholder="Nhập mật khẩu mới..." required minlength="6">
                    </div>
                </div>
                <div class="modal-footer border-0">
                    <button type="button" class="btn btn-light rounded-pill" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary rounded-pill px-4">Xác nhận đặt lại</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    function openResetModal(id, email) {
        document.getElementById('resetUserId').value = id;
        document.getElementById('resetUserEmail').textContent = email;
        document.getElementById('newPasswordInput').value = '';
        new bootstrap.Modal(document.getElementById('resetPasswordModal')).show();
    }
</script>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
