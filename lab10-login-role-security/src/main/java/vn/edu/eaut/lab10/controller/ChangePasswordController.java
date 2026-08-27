package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.ActivityLogService;
import vn.edu.eaut.lab10.service.AuthService;

import java.io.IOException;

@WebServlet("/user/change-password")
public class ChangePasswordController extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final ActivityLogService logService = new ActivityLogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/user/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User sessionUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (oldPassword == null || oldPassword.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ các trường thông tin.");
            request.getRequestDispatcher("/user/change-password.jsp").forward(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("/user/change-password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("/user/change-password.jsp").forward(request, response);
            return;
        }

        boolean success = authService.changePassword(sessionUser.getId(), oldPassword, newPassword);

        if (success) {
            logService.log(sessionUser.getEmail(), sessionUser.getRole().name(), "CHANGE_PASSWORD",
                    "Đổi mật khẩu thành công", request.getRemoteAddr(), "SUCCESS");
            request.setAttribute("success", "Đổi mật khẩu thành công! Hãy ghi nhớ mật khẩu mới của bạn.");
        } else {
            logService.log(sessionUser.getEmail(), sessionUser.getRole().name(), "CHANGE_PASSWORD_FAILED",
                    "Đổi mật khẩu thất bại (mật khẩu cũ không chính xác)", request.getRemoteAddr(), "FAILED");
            request.setAttribute("error", "Mật khẩu hiện tại không chính xác.");
        }

        request.getRequestDispatcher("/user/change-password.jsp").forward(request, response);
    }
}
