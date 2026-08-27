package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.ActivityLogService;
import vn.edu.eaut.lab10.service.SinhVienService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@WebServlet("/staff/sinh-vien")
public class SinhVienController extends HttpServlet {

    private final SinhVienService service = new SinhVienService();
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
                delete(request, response, currentUser);
                break;
            case "list":
            default:
                list(request, response);
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
                create(request, response, currentUser);
                break;
            case "update":
                update(request, response, currentUser);
                break;
            default:
                list(request, response);
                break;
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<SinhVien> list = service.search(keyword);
        request.setAttribute("sinhVienList", list);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/staff/sinhvien/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("sinhVien", new SinhVien());
        request.setAttribute("isEdit", false);
        request.getRequestDispatcher("/staff/sinhvien/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            SinhVien sv = service.findById(id);
            if (sv == null) {
                response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?error=notfound");
                return;
            }
            request.setAttribute("sinhVien", sv);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/staff/sinhvien/form.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?error=invalid_id");
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        String maSinhVien = request.getParameter("maSinhVien");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String lop = request.getParameter("lop");
        String gpaStr = request.getParameter("gpa");
        String ngaySinhStr = request.getParameter("ngaySinh");

        Double gpa = 0.0;
        try {
            if (gpaStr != null && !gpaStr.trim().isEmpty()) {
                gpa = Double.parseDouble(gpaStr.trim());
            }
        } catch (Exception ignored) {
        }

        LocalDate ngaySinh = null;
        try {
            if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty()) {
                ngaySinh = LocalDate.parse(ngaySinhStr.trim());
            }
        } catch (Exception ignored) {
        }

        SinhVien sv = new SinhVien(maSinhVien, hoTen, email, lop, gpa, ngaySinh);
        Map<String, String> errors = service.validate(sv, false);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("sinhVien", sv);
            request.setAttribute("isEdit", false);
            request.getRequestDispatcher("/staff/sinhvien/form.jsp").forward(request, response);
            return;
        }

        service.save(sv);
        logService.log(currentUser.getEmail(), currentUser.getRole().name(), "CREATE_STUDENT",
                "Thêm sinh viên mới: " + maSinhVien + " - " + hoTen, request.getRemoteAddr(), "SUCCESS");

        response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?msg=create_success");
    }

    private void update(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String maSinhVien = request.getParameter("maSinhVien");
            String hoTen = request.getParameter("hoTen");
            String email = request.getParameter("email");
            String lop = request.getParameter("lop");
            String gpaStr = request.getParameter("gpa");
            String ngaySinhStr = request.getParameter("ngaySinh");

            Double gpa = 0.0;
            try {
                if (gpaStr != null && !gpaStr.trim().isEmpty()) {
                    gpa = Double.parseDouble(gpaStr.trim());
                }
            } catch (Exception ignored) {
            }

            LocalDate ngaySinh = null;
            try {
                if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty()) {
                    ngaySinh = LocalDate.parse(ngaySinhStr.trim());
                }
            } catch (Exception ignored) {
            }

            SinhVien sv = new SinhVien(id, maSinhVien, hoTen, email, lop, gpa, ngaySinh);
            Map<String, String> errors = service.validate(sv, true);

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("sinhVien", sv);
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/staff/sinhvien/form.jsp").forward(request, response);
                return;
            }

            service.update(sv);
            logService.log(currentUser.getEmail(), currentUser.getRole().name(), "UPDATE_STUDENT",
                    "Cập nhật sinh viên ID " + id + " (" + maSinhVien + " - " + hoTen + ")", request.getRemoteAddr(), "SUCCESS");

            response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?msg=update_success");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?error=update_failed");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            SinhVien sv = service.findById(id);
            if (sv != null) {
                service.delete(id);
                logService.log(currentUser.getEmail(), currentUser.getRole().name(), "DELETE_STUDENT",
                        "Xóa sinh viên ID " + id + " (" + sv.getMaSinhVien() + ")", request.getRemoteAddr(), "SUCCESS");
                response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?msg=delete_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?error=notfound");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/sinh-vien?error=delete_failed");
        }
    }
}
