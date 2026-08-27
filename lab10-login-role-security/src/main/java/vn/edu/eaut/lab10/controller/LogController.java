package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.ActivityLog;
import vn.edu.eaut.lab10.service.ActivityLogService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/logs")
public class LogController extends HttpServlet {

    private final ActivityLogService logService = new ActivityLogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<ActivityLog> logs = logService.search(keyword);

        request.setAttribute("logList", logs);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/admin/audit-logs.jsp").forward(request, response);
    }
}
