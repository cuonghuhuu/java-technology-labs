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
import vn.edu.eaut.lab10.service.UserService;

import java.io.IOException;

@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final UserService userService = new UserService();
    private final ActivityLogService logService = new ActivityLogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User sessionUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Fetch fresh user data from DB
        User user = userService.findById(sessionUser.getId());
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
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

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        if (fullName == null || fullName.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Họ tên và email không được để trống.");
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
            return;
        }

        boolean updated = authService.updateProfile(sessionUser.getId(), fullName, email);

        if (updated) {
            // Update current user in session
            User freshUser = userService.findById(sessionUser.getId());
            session.setAttribute("currentUser", freshUser);

            logService.log(freshUser.getEmail(), freshUser.getRole().name(), "UPDATE_PROFILE",
                    "Cập nhật thông tin cá nhân: " + fullName + " (" + email + ")", request.getRemoteAddr(), "SUCCESS");

            request.setAttribute("success", "Cập nhật thông tin hồ sơ thành công!");
            request.setAttribute("user", freshUser);
        } else {
            request.setAttribute("error", "Không thể cập nhật hồ sơ. Email này có thể đã được người khác sử dụng.");
            request.setAttribute("user", sessionUser);
        }

        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }
}
