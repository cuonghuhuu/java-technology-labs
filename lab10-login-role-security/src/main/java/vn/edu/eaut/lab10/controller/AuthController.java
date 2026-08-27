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

@WebServlet("/auth")
public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final ActivityLogService logService = new ActivityLogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("currentUser");
                if (user != null) {
                    logService.log(user.getEmail(), user.getRole().name(), "LOGOUT",
                            "Người dùng đăng xuất khỏi hệ thống", request.getRemoteAddr(), "SUCCESS");
                }
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp?logout=true");
            return;
        }

        // Default redirect to login.jsp
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String redirect = request.getParameter("redirect");

        User user = authService.login(email, password);

        if (user == null) {
            logService.log(email, "GUEST", "LOGIN_FAILED",
                    "Đăng nhập thất bại (sai email/mật khẩu hoặc tài khoản bị khóa)", request.getRemoteAddr(), "FAILED");

            request.setAttribute("error", "Email hoặc mật khẩu không chính xác, hoặc tài khoản đã bị khóa.");
            request.setAttribute("inputEmail", email);
            request.setAttribute("redirect", redirect);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Login success
        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", user);
        session.setAttribute("loginTime", System.currentTimeMillis());

        logService.log(user.getEmail(), user.getRole().name(), "LOGIN_SUCCESS",
                "Đăng nhập thành công với vai trò: " + user.getRole().name(), request.getRemoteAddr(), "SUCCESS");

        if (redirect != null && !redirect.trim().isEmpty() && !redirect.contains("login") && !redirect.contains("auth")) {
            response.sendRedirect(redirect);
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}
