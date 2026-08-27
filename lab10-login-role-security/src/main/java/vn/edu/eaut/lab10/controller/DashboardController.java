package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.service.*;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private final SinhVienService sinhVienService = new SinhVienService();
    private final SachService sachService = new SachService();
    private final SanPhamService sanPhamService = new SanPhamService();
    private final UserService userService = new UserService();
    private final ActivityLogService logService = new ActivityLogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Provide statistics for dashboard view
        request.setAttribute("totalStudents", sinhVienService.count());
        request.setAttribute("totalBooks", sachService.count());
        request.setAttribute("totalProducts", sanPhamService.count());
        request.setAttribute("totalUsers", userService.count());
        request.setAttribute("recentLogs", logService.findRecent(8));

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
