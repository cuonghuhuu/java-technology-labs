package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.Sach;
import vn.edu.eaut.lab7.repository.SachRepository;
import java.io.IOException;

@WebServlet("/sach")
public class SachController extends HttpServlet {
    private final SachRepository repository = new SachRepository();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) forward(req, resp, "/views/sach/form.jsp");
            else if ("edit".equals(action)) { req.setAttribute("sach", require(req)); forward(req, resp, "/views/sach/form.jsp"); }
            else if ("detail".equals(action)) { req.setAttribute("sach", require(req)); forward(req, resp, "/views/sach/detail.jsp"); }
            else if ("delete".equals(action)) { repository.delete(Integer.parseInt(req.getParameter("id"))); redirect(resp, req, "Đã xóa sách."); }
            else { req.setAttribute("dsSach", repository.search(req.getParameter("keyword"))); req.setAttribute("keyword", req.getParameter("keyword")); forward(req, resp, "/views/sach/list.jsp"); }
        } catch (Exception e) { redirectError(resp, req, "Không tìm thấy sách hoặc ID không hợp lệ."); }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Sach sach = new Sach(number(req.getParameter("id")), text(req, "maSach"), text(req, "tenSach"), text(req, "tacGia"), text(req, "nhaXuatBan"), number(req.getParameter("namXuatBan")));
            String error = sach.getMaSach().isBlank() || sach.getTenSach().isBlank() || sach.getTacGia().isBlank() || sach.getNhaXuatBan().isBlank() || sach.getNamXuatBan() < 1 ? "Vui lòng nhập đầy đủ thông tin sách hợp lệ." : null;
            if (error != null) { req.setAttribute("error", error); req.setAttribute("sach", sach); forward(req, resp, "/views/sach/form.jsp"); return; }
            if (sach.getId() == 0) { repository.add(sach); redirect(resp, req, "Thêm sách thành công."); } else { repository.update(sach); redirect(resp, req, "Cập nhật sách thành công."); }
        } catch (NumberFormatException e) { req.setAttribute("error", "Năm xuất bản phải là số nguyên."); forward(req, resp, "/views/sach/form.jsp"); }
    }
    private Sach require(HttpServletRequest req) { Sach sach = repository.findById(Integer.parseInt(req.getParameter("id"))); if (sach == null) throw new IllegalArgumentException(); return sach; }
    private int number(String value) { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); }
    private String text(HttpServletRequest req, String name) { String value = req.getParameter(name); return value == null ? "" : value.trim(); }
    private void forward(HttpServletRequest req, HttpServletResponse resp, String view) throws ServletException, IOException { req.getRequestDispatcher(view).forward(req, resp); }
    private void redirect(HttpServletResponse resp, HttpServletRequest req, String message) throws IOException { resp.sendRedirect(req.getContextPath() + "/sach?success=" + java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8)); }
    private void redirectError(HttpServletResponse resp, HttpServletRequest req, String message) throws IOException { resp.sendRedirect(req.getContextPath() + "/sach?error=" + java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8)); }
}
