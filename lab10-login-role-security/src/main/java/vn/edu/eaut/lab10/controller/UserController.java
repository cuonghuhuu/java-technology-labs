package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.ActivityLogService;
import vn.edu.eaut.lab10.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/users")
public class UserController extends HttpServlet {

    private final UserService userService = new UserService();
    private final ActivityLogService logService = new ActivityLogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        User currentUser = (User) request.getSession().getAttribute("currentUser");

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteUser(request, response, currentUser);
                break;
            case "toggle-status":
                toggleStatus(request, response, currentUser);
                break;
            case "list":
            default:
                listUsers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        User currentUser = (User) request.getSession().getAttribute("currentUser");

        switch (action) {
            case "create":
                createUser(request, response, currentUser);
                break;
            case "update":
                updateUser(request, response, currentUser);
                break;
            case "reset-password":
                resetPassword(request, response, currentUser);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String roleStr = request.getParameter("role");

        Role roleFilter = null;
        if (roleStr != null && !roleStr.trim().isEmpty()) {
            try {
                roleFilter = Role.valueOf(roleStr.trim());
            } catch (Exception ignored) {
            }
        }

        List<User> list = userService.search(keyword, roleFilter);
        request.setAttribute("userList", list);
        request.setAttribute("keyword", keyword);
        request.setAttribute("selectedRole", roleStr);
        request.setAttribute("roles", Role.values());

        request.getRequestDispatcher("/admin/user-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("user", new User());
        request.setAttribute("roles", Role.values());
        request.setAttribute("isEdit", false);
        request.getRequestDispatcher("/admin/user-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userService.findById(id);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/admin/users?error=notfound");
                return;
            }
            request.setAttribute("user", user);
            request.setAttribute("roles", Role.values());
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/admin/user-form.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/users?error=invalid_id");
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String roleStr = request.getParameter("role");
        String password = request.getParameter("password");
        boolean active = "on".equals(request.getParameter("active")) || "true".equals(request.getParameter("active"));

        Role role = null;
        try {
            role = Role.valueOf(roleStr);
        } catch (Exception ignored) {
        }

        User newUser = new User(email, "", fullName, role, active);
        Map<String, String> errors = userService.validate(newUser, password, false);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("user", newUser);
            request.setAttribute("roles", Role.values());
            request.setAttribute("isEdit", false);
            request.getRequestDispatcher("/admin/user-form.jsp").forward(request, response);
            return;
        }

        userService.createUser(newUser, password);
        logService.log(currentUser.getEmail(), currentUser.getRole().name(), "CREATE_USER",
                "Tạo người dùng mới: " + email + " với vai trò " + role, request.getRemoteAddr(), "SUCCESS");

        response.sendRedirect(request.getContextPath() + "/admin/users?msg=create_success");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String roleStr = request.getParameter("role");
            boolean active = "on".equals(request.getParameter("active")) || "true".equals(request.getParameter("active"));

            Role role = null;
            try {
                role = Role.valueOf(roleStr);
            } catch (Exception ignored) {
            }

            User editUser = new User(id, email, "", fullName, role, active);
            Map<String, String> errors = userService.validate(editUser, null, true);

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("user", editUser);
                request.setAttribute("roles", Role.values());
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/admin/user-form.jsp").forward(request, response);
                return;
            }

            userService.updateUser(editUser);
            logService.log(currentUser.getEmail(), currentUser.getRole().name(), "UPDATE_USER",
                    "Cập nhật tài khoản ID " + id + " (" + email + ")", request.getRemoteAddr(), "SUCCESS");

            response.sendRedirect(request.getContextPath() + "/admin/users?msg=update_success");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/users?error=update_failed");
        }
    }

    private void toggleStatus(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = userService.toggleStatus(id, currentUser.getId());
            if (success) {
                logService.log(currentUser.getEmail(), currentUser.getRole().name(), "TOGGLE_USER_STATUS",
                        "Thay đổi trạng thái tài khoản ID " + id, request.getRemoteAddr(), "SUCCESS");
                response.sendRedirect(request.getContextPath() + "/admin/users?msg=status_toggled");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/users?error=self_lock_forbidden");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/users?error=invalid_id");
        }
    }

    private void resetPassword(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String newPassword = request.getParameter("newPassword");
            boolean success = userService.resetPassword(id, newPassword);
            if (success) {
                logService.log(currentUser.getEmail(), currentUser.getRole().name(), "RESET_PASSWORD",
                        "Đặt lại mật khẩu cho tài khoản ID " + id, request.getRemoteAddr(), "SUCCESS");
                response.sendRedirect(request.getContextPath() + "/admin/users?msg=reset_pass_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/users?error=reset_pass_failed");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/users?error=invalid_id");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = userService.deleteUser(id, currentUser.getId());
            if (success) {
                logService.log(currentUser.getEmail(), currentUser.getRole().name(), "DELETE_USER",
                        "Xóa tài khoản ID " + id, request.getRemoteAddr(), "SUCCESS");
                response.sendRedirect(request.getContextPath() + "/admin/users?msg=delete_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/users?error=self_delete_forbidden");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/users?error=delete_failed");
        }
    }
}
