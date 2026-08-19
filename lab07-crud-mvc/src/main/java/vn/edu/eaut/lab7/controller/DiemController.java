package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.DiemSinhVien;
import vn.edu.eaut.lab7.repository.DiemRepository;
import java.io.IOException;

@WebServlet("/diem")
public class DiemController extends HttpServlet {
    private final DiemRepository repository = new DiemRepository();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) view(req, resp, "/views/diem/form.jsp");
            else if ("edit".equals(action)) { req.setAttribute("diem", find(req)); view(req, resp, "/views/diem/form.jsp"); }
            else if ("detail".equals(action)) { req.setAttribute("diem", find(req)); view(req, resp, "/views/diem/detail.jsp"); }
            else if ("delete".equals(action)) { repository.delete(Integer.parseInt(req.getParameter("id"))); success(req, resp, "Đã xóa bảng điểm."); }
            else { req.setAttribute("dsDiem", repository.findAll()); view(req, resp, "/views/diem/list.jsp"); }
        } catch (Exception e) { error(req, resp, "ID bảng điểm không hợp lệ."); }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        DiemSinhVien diem = new DiemSinhVien();
        try {
            diem = new DiemSinhVien(number(req.getParameter("id")), text(req, "maSinhVien"), text(req, "hoTen"), score(req, "diemChuyenCan"), score(req, "diemGiuaKy"), score(req, "diemCuoiKy"));
            String message = diem.getMaSinhVien().isBlank() || diem.getHoTen().isBlank() ? "Mã sinh viên và họ tên không được để trống." : valid(diem) ? null : "Các điểm phải nằm trong khoảng từ 0 đến 10.";
            if (message != null) { req.setAttribute("error", message); req.setAttribute("diem", diem); view(req, resp, "/views/diem/form.jsp"); return; }
            if (diem.getId() == 0) { repository.add(diem); success(req, resp, "Thêm điểm thành công."); } else { repository.update(diem); success(req, resp, "Cập nhật điểm thành công."); }
        } catch (NumberFormatException e) { req.setAttribute("error", "Điểm phải là số hợp lệ."); req.setAttribute("diem", diem); view(req, resp, "/views/diem/form.jsp"); }
    }
    private boolean valid(DiemSinhVien d) { return d.getDiemChuyenCan() >= 0 && d.getDiemChuyenCan() <= 10 && d.getDiemGiuaKy() >= 0 && d.getDiemGiuaKy() <= 10 && d.getDiemCuoiKy() >= 0 && d.getDiemCuoiKy() <= 10; }
    private DiemSinhVien find(HttpServletRequest req) { DiemSinhVien d = repository.findById(Integer.parseInt(req.getParameter("id"))); if (d == null) throw new IllegalArgumentException(); return d; }
    private int number(String v) { return v == null || v.isBlank() ? 0 : Integer.parseInt(v); }
    private double score(HttpServletRequest q, String n) { return Double.parseDouble(q.getParameter(n)); }
    private String text(HttpServletRequest q, String n) { String v = q.getParameter(n); return v == null ? "" : v.trim(); }
    private void view(HttpServletRequest q, HttpServletResponse p, String v) throws ServletException, IOException { q.getRequestDispatcher(v).forward(q, p); }
    private void success(HttpServletRequest q, HttpServletResponse p, String m) throws IOException { p.sendRedirect(q.getContextPath() + "/diem?success=" + java.net.URLEncoder.encode(m, java.nio.charset.StandardCharsets.UTF_8)); }
    private void error(HttpServletRequest q, HttpServletResponse p, String m) throws IOException { p.sendRedirect(q.getContextPath() + "/diem?error=" + java.net.URLEncoder.encode(m, java.nio.charset.StandardCharsets.UTF_8)); }
}
