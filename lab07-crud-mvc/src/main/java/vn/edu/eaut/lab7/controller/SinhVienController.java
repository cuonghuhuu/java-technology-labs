package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.edu.eaut.lab7.model.SinhVien;
import vn.edu.eaut.lab7.repository.SinhVienRepository;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/sinh-vien")
public class SinhVienController extends HttpServlet {

    private final SinhVienRepository repository =
            new SinhVienRepository();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.isBlank()) {
            action = "list";
        }

        try {

            switch (action) {

                case "new":
                    showForm(request, response);
                    break;

                case "edit":
                    showEditForm(request, response);
                    break;

                case "detail":
                    showDetail(request, response);
                    break;

                case "delete":
                    deleteStudent(request, response);
                    break;

                default:
                    showList(request, response);
                    break;
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/sinh-vien?error="
                            + encode("ID sinh viên không hợp lệ")
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String idValue = request.getParameter("id");
        String maSinhVien = trim(request.getParameter("maSinhVien"));
        String hoTen = trim(request.getParameter("hoTen"));
        String email = trim(request.getParameter("email"));
        String lop = trim(request.getParameter("lop"));

        int id = 0;

        if (idValue != null && !idValue.isBlank()) {
            id = Integer.parseInt(idValue);
        }

        SinhVien sinhVien = new SinhVien(
                id,
                maSinhVien,
                hoTen,
                email,
                lop
        );

        String error = validate(sinhVien);

        if (error == null
                && repository.existsMaSinhVien(maSinhVien, id)) {
            error = "Mã sinh viên đã tồn tại.";
        }

        if (error != null) {

            request.setAttribute("error", error);
            request.setAttribute("sv", sinhVien);

            request.getRequestDispatcher(
                    "/views/sinhvien/form.jsp"
            ).forward(request, response);

            return;
        }

        if (id == 0) {

            repository.add(sinhVien);

            response.sendRedirect(
                    request.getContextPath()
                            + "/sinh-vien?success="
                            + encode("Thêm sinh viên thành công")
            );

        } else {

            repository.update(sinhVien);

            response.sendRedirect(
                    request.getContextPath()
                            + "/sinh-vien?success="
                            + encode("Cập nhật sinh viên thành công")
            );
        }
    }

    private void showList(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        List<SinhVien> ketQua = repository.search(keyword);
        int pageSize = 5;
        int totalPages = Math.max(1, (int) Math.ceil((double) ketQua.size() / pageSize));
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException ignored) {
            // Trang đầu tiên khi không có hoặc sai tham số page.
        }
        currentPage = Math.max(1, Math.min(currentPage, totalPages));
        int fromIndex = Math.min((currentPage - 1) * pageSize, ketQua.size());
        int toIndex = Math.min(fromIndex + pageSize, ketQua.size());

        request.setAttribute("dsSinhVien", ketQua.subList(fromIndex, toIndex));
        request.setAttribute("totalSinhVien", ketQua.size());
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword == null ? "" : keyword);

        request.getRequestDispatcher(
                "/views/sinhvien/list.jsp"
        ).forward(request, response);
    }

    private void showForm(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher(
                "/views/sinhvien/form.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        SinhVien sinhVien = repository.findById(id);

        if (sinhVien == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/sinh-vien?error="
                            + encode("Không tìm thấy sinh viên")
            );

            return;
        }

        request.setAttribute("sv", sinhVien);

        request.getRequestDispatcher(
                "/views/sinhvien/form.jsp"
        ).forward(request, response);
    }

    private void showDetail(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        SinhVien sinhVien = repository.findById(id);

        if (sinhVien == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/sinh-vien?error="
                            + encode("Không tìm thấy sinh viên")
            );

            return;
        }

        request.setAttribute("sv", sinhVien);

        request.getRequestDispatcher(
                "/views/sinhvien/detail.jsp"
        ).forward(request, response);
    }

    private void deleteStudent(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        repository.delete(id);

        response.sendRedirect(
                request.getContextPath()
                        + "/sinh-vien?success="
                        + encode("Xóa sinh viên thành công")
        );
    }

    private String validate(SinhVien sv) {

        if (sv.getMaSinhVien().isBlank()) {
            return "Mã sinh viên không được để trống.";
        }

        if (sv.getHoTen().isBlank()) {
            return "Họ tên không được để trống.";
        }

        if (sv.getEmail().isBlank()) {
            return "Email không được để trống.";
        }

        if (!sv.getEmail().contains("@")) {
            return "Email không đúng định dạng.";
        }

        if (sv.getLop().isBlank()) {
            return "Lớp không được để trống.";
        }

        return null;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private String encode(String value) {
        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8
        );
    }
}
