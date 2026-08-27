package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Sach;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.ActivityLogService;
import vn.edu.eaut.lab10.service.SachService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/staff/sach")
public class SachController extends HttpServlet {

    private final SachService service = new SachService();
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
        List<Sach> list = service.search(keyword);
        request.setAttribute("sachList", list);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/staff/sach/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("sach", new Sach());
        request.setAttribute("isEdit", false);
        request.getRequestDispatcher("/staff/sach/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Sach sach = service.findById(id);
            if (sach == null) {
                response.sendRedirect(request.getContextPath() + "/staff/sach?error=notfound");
                return;
            }
            request.setAttribute("sach", sach);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/staff/sach/form.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/sach?error=invalid_id");
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        String maSach = request.getParameter("maSach");
        String tenSach = request.getParameter("tenSach");
        String tacGia = request.getParameter("tacGia");
        String theLoai = request.getParameter("theLoai");
        String namXuatBanStr = request.getParameter("namXuatBan");
        String giaStr = request.getParameter("gia");
        String soLuongStr = request.getParameter("soLuong");

        Integer namXuatBan = null;
        try {
            if (namXuatBanStr != null && !namXuatBanStr.trim().isEmpty()) {
                namXuatBan = Integer.parseInt(namXuatBanStr.trim());
            }
        } catch (Exception ignored) {
        }

        Double gia = 0.0;
        try {
            if (giaStr != null && !giaStr.trim().isEmpty()) {
                gia = Double.parseDouble(giaStr.trim());
            }
        } catch (Exception ignored) {
        }

        Integer soLuong = 0;
        try {
            if (soLuongStr != null && !soLuongStr.trim().isEmpty()) {
                soLuong = Integer.parseInt(soLuongStr.trim());
            }
        } catch (Exception ignored) {
        }

        Sach sach = new Sach(maSach, tenSach, tacGia, theLoai, namXuatBan, gia, soLuong);
        Map<String, String> errors = service.validate(sach, false);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("sach", sach);
            request.setAttribute("isEdit", false);
            request.getRequestDispatcher("/staff/sach/form.jsp").forward(request, response);
            return;
        }

        service.save(sach);
        logService.log(currentUser.getEmail(), currentUser.getRole().name(), "CREATE_BOOK",
                "Thêm sách mới: " + maSach + " - " + tenSach, request.getRemoteAddr(), "SUCCESS");

        response.sendRedirect(request.getContextPath() + "/staff/sach?msg=create_success");
    }

    private void update(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String maSach = request.getParameter("maSach");
            String tenSach = request.getParameter("tenSach");
            String tacGia = request.getParameter("tacGia");
            String theLoai = request.getParameter("theLoai");
            String namXuatBanStr = request.getParameter("namXuatBan");
            String giaStr = request.getParameter("gia");
            String soLuongStr = request.getParameter("soLuong");

            Integer namXuatBan = null;
            try {
                if (namXuatBanStr != null && !namXuatBanStr.trim().isEmpty()) {
                    namXuatBan = Integer.parseInt(namXuatBanStr.trim());
                }
            } catch (Exception ignored) {
            }

            Double gia = 0.0;
            try {
                if (giaStr != null && !giaStr.trim().isEmpty()) {
                    gia = Double.parseDouble(giaStr.trim());
                }
            } catch (Exception ignored) {
            }

            Integer soLuong = 0;
            try {
                if (soLuongStr != null && !soLuongStr.trim().isEmpty()) {
                    soLuong = Integer.parseInt(soLuongStr.trim());
                }
            } catch (Exception ignored) {
            }

            Sach sach = new Sach(id, maSach, tenSach, tacGia, theLoai, namXuatBan, gia, soLuong);
            Map<String, String> errors = service.validate(sach, true);

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("sach", sach);
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/staff/sach/form.jsp").forward(request, response);
                return;
            }

            service.update(sach);
            logService.log(currentUser.getEmail(), currentUser.getRole().name(), "UPDATE_BOOK",
                    "Cập nhật sách ID " + id + " (" + maSach + " - " + tenSach + ")", request.getRemoteAddr(), "SUCCESS");

            response.sendRedirect(request.getContextPath() + "/staff/sach?msg=update_success");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/sach?error=update_failed");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Sach sach = service.findById(id);
            if (sach != null) {
                service.delete(id);
                logService.log(currentUser.getEmail(), currentUser.getRole().name(), "DELETE_BOOK",
                        "Xóa sách ID " + id + " (" + sach.getMaSach() + ")", request.getRemoteAddr(), "SUCCESS");
                response.sendRedirect(request.getContextPath() + "/staff/sach?msg=delete_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/staff/sach?error=notfound");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/sach?error=delete_failed");
        }
    }
}
