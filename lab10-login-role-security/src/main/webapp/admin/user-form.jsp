<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${isEdit ? 'Chỉnh Sửa Tài Khoản' : 'Thêm Tài Khoản Mới'} - Admin" />
<c:set var="activeGroup" value="admin" />
<c:set var="activePage" value="users" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/navbar.jspf" %>

<div class="container main-content py-4">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card border-0 shadow-sm rounded-4 p-4 bg-white">
                <div class="d-flex align-items-center gap-3 mb-4 pb-3 border-bottom">
                    <div class="bg-danger-subtle text-danger rounded-circle p-3 fs-3">
                        <i class="bi ${isEdit ? 'bi-person-gear' : 'bi-person-plus-fill'}"></i>
                    </div>
                    <div>
                        <h4 class="fw-bold mb-0">${isEdit ? 'Chỉnh Sửa Tài Khoản' : 'Thêm Tài Khoản Mới'}</h4>
                        <p class="text-muted small mb-0">Quản trị và phân quyền tài khoản người dùng trong hệ thống</p>
                    </div>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/admin/users?action=${isEdit ? 'update' : 'create'}">
                    <c:if test="${isEdit}">
                        <input type="hidden" name="id" value="${user.id}">
                    </c:if>

                    <div class="mb-3">
                        <label for="fullName" class="form-label">Họ và Tên <span class="text-danger">*</span></label>
                        <input type="text" class="form-control ${not empty errors.fullName ? 'is-invalid' : ''}"
                               id="fullName" name="fullName" value="<c:out value='${user.fullName}'/>"
                               placeholder="vd: Nguyễn Văn A" required>
                        <c:if test="${not empty errors.fullName}">
                            <div class="invalid-feedback">${errors.fullName}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Địa chỉ Email <span class="text-danger">*</span></label>
                        <input type="email" class="form-control ${not empty errors.email ? 'is-invalid' : ''}"
                               id="email" name="email" value="<c:out value='${user.email}'/>"
                               placeholder="vd: user@eaut.edu.vn" required>
                        <c:if test="${not empty errors.email}">
                            <div class="invalid-feedback">${errors.email}</div>
                        </c:if>
                    </div>

                    <c:if test="${!isEdit}">
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật khẩu khởi tạo <span class="text-danger">*</span></label>
                            <input type="password" class="form-control ${not empty errors.password ? 'is-invalid' : ''}"
                                   id="password" name="password" placeholder="Tối thiểu 6 ký tự" required minlength="6">
                            <c:if test="${not empty errors.password}">
                                <div class="invalid-feedback">${errors.password}</div>
                            </c:if>
                        </div>
                    </c:if>

                    <div class="mb-3">
                        <label for="role" class="form-label">Vai trò (Role) <span class="text-danger">*</span></label>
                        <select name="role" id="role" class="form-select ${not empty errors.role ? 'is-invalid' : ''}" required>
                            <c:forEach var="r" items="${roles}">
                                <option value="${r}" ${user.role == r ? 'selected' : ''}>${r} (${r.displayName})</option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty errors.role}">
                            <div class="invalid-feedback">${errors.role}</div>
                        </c:if>
                    </div>

                    <div class="mb-4 form-check form-switch">
                        <input class="form-check-input" type="checkbox" role="switch" id="activeSwitch"
                               name="active" ${user.active || !isEdit ? 'checked' : ''}>
                        <label class="form-check-label fw-semibold" for="activeSwitch">Kích hoạt tài khoản</label>
                    </div>

                    <div class="d-flex gap-2 justify-content-end">
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary px-4 rounded-pill">
                            Hủy bỏ
                        </a>
                        <button type="submit" class="btn btn-danger px-4 rounded-pill shadow-sm">
                            <i class="bi bi-floppy me-1"></i> ${isEdit ? 'Lưu Thay Đổi' : 'Tạo Tài Khoản'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
