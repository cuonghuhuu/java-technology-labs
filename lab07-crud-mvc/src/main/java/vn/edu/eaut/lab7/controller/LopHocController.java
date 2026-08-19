package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.LopHoc;
import vn.edu.eaut.lab7.repository.LopHocRepository;
import java.io.IOException;

@WebServlet("/lop-hoc")
public class LopHocController extends HttpServlet {
    private final LopHocRepository repository = new LopHocRepository();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) view(req, resp, "/views/lophoc/form.jsp");
            else if ("edit".equals(action)) { req.setAttribute("lopHoc", find(req)); view(req, resp, "/views/lophoc/form.jsp"); }
            else if ("delete".equals(action)) { repository.delete(Integer.parseInt(req.getParameter("id"))); success(req, resp, "Đã xóa lớp học."); }
            else { req.setAttribute("dsLopHoc", repository.search(req.getParameter("keyword"))); req.setAttribute("keyword", req.getParameter("keyword")); view(req, resp, "/views/lophoc/list.jsp"); }
        } catch (Exception e) { error(req, resp, "ID lớp học không hợp lệ."); }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LopHoc lop = new LopHoc();
        try {
            lop = new LopHoc(number(req.getParameter("id")), text(req, "maLop"), text(req, "tenLop"), text(req, "coVanHocTap"), number(req.getParameter("soLuongSinhVien")));
            String message = lop.getMaLop().isBlank() || lop.getTenLop().isBlank() || lop.getCoVanHocTap().isBlank() ? "Vui lòng nhập đầy đủ thông tin lớp học." : lop.getSoLuongSinhVien() < 0 ? "Số lượng sinh viên phải từ 0 trở lên." : null;
            if (message != null) { req.setAttribute("error", message); req.setAttribute("lopHoc", lop); view(req, resp, "/views/lophoc/form.jsp"); return; }
            if (lop.getId() == 0) { repository.add(lop); success(req, resp, "Thêm lớp học thành công."); } else { repository.update(lop); success(req, resp, "Cập nhật lớp học thành công."); }
        } catch (NumberFormatException e) { req.setAttribute("error", "Số lượng sinh viên phải là số nguyên."); req.setAttribute("lopHoc", lop); view(req, resp, "/views/lophoc/form.jsp"); }
    }
    private LopHoc find(HttpServletRequest req) { LopHoc l = repository.findById(Integer.parseInt(req.getParameter("id"))); if (l == null) throw new IllegalArgumentException(); return l; }
    private int number(String v) { return v == null || v.isBlank() ? 0 : Integer.parseInt(v); }
    private String text(HttpServletRequest req, String n) { String v = req.getParameter(n); return v == null ? "" : v.trim(); }
    private void view(HttpServletRequest q, HttpServletResponse p, String v) throws ServletException, IOException { q.getRequestDispatcher(v).forward(q, p); }
    private void success(HttpServletRequest q, HttpServletResponse p, String m) throws IOException { p.sendRedirect(q.getContextPath() + "/lop-hoc?success=" + java.net.URLEncoder.encode(m, java.nio.charset.StandardCharsets.UTF_8)); }
    private void error(HttpServletRequest q, HttpServletResponse p, String m) throws IOException { p.sendRedirect(q.getContextPath() + "/lop-hoc?error=" + java.net.URLEncoder.encode(m, java.nio.charset.StandardCharsets.UTF_8)); }
}
