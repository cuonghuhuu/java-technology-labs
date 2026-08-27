package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.SanPham;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.ActivityLogService;
import vn.edu.eaut.lab10.service.SanPhamService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/staff/san-pham")
public class SanPhamController extends HttpServlet {

    private final SanPhamService service = new SanPhamService();
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
        List<SanPham> list = service.search(keyword);
        request.setAttribute("sanPhamList", list);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/staff/sanpham/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("sanPham", new SanPham());
        request.setAttribute("isEdit", false);
        request.getRequestDispatcher("/staff/sanpham/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            SanPham sp = service.findById(id);
            if (sp == null) {
                response.sendRedirect(request.getContextPath() + "/staff/san-pham?error=notfound");
                return;
            }
            request.setAttribute("sanPham", sp);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/staff/sanpham/form.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/san-pham?error=invalid_id");
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        String maSanPham = request.getParameter("maSanPham");
        String tenSanPham = request.getParameter("tenSanPham");
        String danhMuc = request.getParameter("danhMuc");
        String giaStr = request.getParameter("gia");
        String soLuongStr = request.getParameter("soLuong");
        String moTa = request.getParameter("moTa");

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

        SanPham sp = new SanPham(maSanPham, tenSanPham, danhMuc, gia, soLuong, moTa);
        Map<String, String> errors = service.validate(sp, false);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("sanPham", sp);
            request.setAttribute("isEdit", false);
            request.getRequestDispatcher("/staff/sanpham/form.jsp").forward(request, response);
            return;
        }

        service.save(sp);
        logService.log(currentUser.getEmail(), currentUser.getRole().name(), "CREATE_PRODUCT",
                "Thêm sản phẩm mới: " + maSanPham + " - " + tenSanPham, request.getRemoteAddr(), "SUCCESS");

        response.sendRedirect(request.getContextPath() + "/staff/san-pham?msg=create_success");
    }

    private void update(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String maSanPham = request.getParameter("maSanPham");
            String tenSanPham = request.getParameter("tenSanPham");
            String danhMuc = request.getParameter("danhMuc");
            String giaStr = request.getParameter("gia");
            String soLuongStr = request.getParameter("soLuong");
            String moTa = request.getParameter("moTa");

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

            SanPham sp = new SanPham(id, maSanPham, tenSanPham, danhMuc, gia, soLuong, moTa);
            Map<String, String> errors = service.validate(sp, true);

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("sanPham", sp);
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/staff/sanpham/form.jsp").forward(request, response);
                return;
            }

            service.update(sp);
            logService.log(currentUser.getEmail(), currentUser.getRole().name(), "UPDATE_PRODUCT",
                    "Cập nhật sản phẩm ID " + id + " (" + maSanPham + " - " + tenSanPham + ")", request.getRemoteAddr(), "SUCCESS");

            response.sendRedirect(request.getContextPath() + "/staff/san-pham?msg=update_success");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/san-pham?error=update_failed");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            SanPham sp = service.findById(id);
            if (sp != null) {
                service.delete(id);
                logService.log(currentUser.getEmail(), currentUser.getRole().name(), "DELETE_PRODUCT",
                        "Xóa sản phẩm ID " + id + " (" + sp.getMaSanPham() + ")", request.getRemoteAddr(), "SUCCESS");
                response.sendRedirect(request.getContextPath() + "/staff/san-pham?msg=delete_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/staff/san-pham?error=notfound");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/staff/san-pham?error=delete_failed");
        }
    }
}
