package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.SanPham;
import vn.edu.eaut.lab7.repository.SanPhamRepository;
import java.io.IOException;

@WebServlet("/san-pham")
public class SanPhamController extends HttpServlet {
    private final SanPhamRepository repository = new SanPhamRepository();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) forward(req, resp, "/views/sanpham/form.jsp");
            else if ("edit".equals(action)) { req.setAttribute("sanPham", require(req)); forward(req, resp, "/views/sanpham/form.jsp"); }
            else if ("detail".equals(action)) { req.setAttribute("sanPham", require(req)); forward(req, resp, "/views/sanpham/detail.jsp"); }
            else if ("delete".equals(action)) { repository.delete(Integer.parseInt(req.getParameter("id"))); redirect(resp, req, "Đã xóa sản phẩm."); }
            else { req.setAttribute("dsSanPham", repository.search(req.getParameter("keyword"))); req.setAttribute("keyword", req.getParameter("keyword")); forward(req, resp, "/views/sanpham/list.jsp"); }
        } catch (Exception e) { redirectError(resp, req, "Không tìm thấy sản phẩm hoặc ID không hợp lệ."); }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        SanPham sanPham = new SanPham();
        try {
            sanPham = new SanPham(number(req.getParameter("id")), text(req, "ma"), text(req, "ten"), text(req, "moTa"), Double.parseDouble(req.getParameter("gia")), number(req.getParameter("soLuong")));
            String error = sanPham.getMa().isBlank() || sanPham.getTen().isBlank() ? "Mã và tên sản phẩm không được để trống." : sanPham.getGia() <= 0 ? "Giá phải lớn hơn 0." : sanPham.getSoLuong() < 0 ? "Số lượng phải từ 0 trở lên." : null;
            if (error != null) { req.setAttribute("error", error); req.setAttribute("sanPham", sanPham); forward(req, resp, "/views/sanpham/form.jsp"); return; }
            if (sanPham.getId() == 0) { repository.add(sanPham); redirect(resp, req, "Thêm sản phẩm thành công."); } else { repository.update(sanPham); redirect(resp, req, "Cập nhật sản phẩm thành công."); }
        } catch (NumberFormatException e) { req.setAttribute("error", "Giá và số lượng phải là số hợp lệ."); req.setAttribute("sanPham", sanPham); forward(req, resp, "/views/sanpham/form.jsp"); }
    }
    private SanPham require(HttpServletRequest req) { SanPham sp = repository.findById(Integer.parseInt(req.getParameter("id"))); if (sp == null) throw new IllegalArgumentException(); return sp; }
    private int number(String value) { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); }
    private String text(HttpServletRequest req, String name) { String value = req.getParameter(name); return value == null ? "" : value.trim(); }
    private void forward(HttpServletRequest req, HttpServletResponse resp, String view) throws ServletException, IOException { req.getRequestDispatcher(view).forward(req, resp); }
    private void redirect(HttpServletResponse resp, HttpServletRequest req, String message) throws IOException { resp.sendRedirect(req.getContextPath() + "/san-pham?success=" + java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8)); }
    private void redirectError(HttpServletResponse resp, HttpServletRequest req, String message) throws IOException { resp.sendRedirect(req.getContextPath() + "/san-pham?error=" + java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8)); }
}
