package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab7.model.CartItem;
import vn.edu.eaut.lab7.model.SanPham;
import vn.edu.eaut.lab7.repository.SanPhamRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/gio-hang")
public class CartController extends HttpServlet {
    private final SanPhamRepository sanPhamRepository = new SanPhamRepository();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                SanPham sp = sanPhamRepository.findById(Integer.parseInt(req.getParameter("id")));
                if (sp == null) throw new IllegalArgumentException();
                List<CartItem> cart = cart(req); CartItem current = cart.stream().filter(i -> i.getSanPhamId() == sp.getId()).findFirst().orElse(null);
                if (current == null) cart.add(new CartItem(sp, 1)); else current.setSoLuong(current.getSoLuong() + 1);
                message(req, resp, "Đã thêm sản phẩm vào giỏ hàng.");
            } else if ("remove".equals(action)) {
                cart(req).removeIf(i -> i.getSanPhamId() == Integer.parseInt(req.getParameter("id")));
                message(req, resp, "Đã xóa sản phẩm khỏi giỏ hàng.");
            } else show(req, resp);
        } catch (Exception e) { resp.sendRedirect(req.getContextPath() + "/gio-hang?error=" + java.net.URLEncoder.encode("Thao tác giỏ hàng không hợp lệ.", java.nio.charset.StandardCharsets.UTF_8)); }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id")); int soLuong = Integer.parseInt(req.getParameter("soLuong"));
            CartItem item = cart(req).stream().filter(i -> i.getSanPhamId() == id).findFirst().orElse(null);
            if (item != null) { if (soLuong <= 0) cart(req).remove(item); else item.setSoLuong(soLuong); }
            message(req, resp, "Đã cập nhật giỏ hàng.");
        } catch (Exception e) { resp.sendRedirect(req.getContextPath() + "/gio-hang?error=" + java.net.URLEncoder.encode("Số lượng không hợp lệ.", java.nio.charset.StandardCharsets.UTF_8)); }
    }
    private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CartItem> cart = cart(req); double total = cart.stream().mapToDouble(CartItem::getThanhTien).sum();
        req.setAttribute("dsSanPham", sanPhamRepository.findAll()); req.setAttribute("cart", cart); req.setAttribute("tongTien", total);
        req.getRequestDispatcher("/views/giohang/cart.jsp").forward(req, resp);
    }
    @SuppressWarnings("unchecked")
    private List<CartItem> cart(HttpServletRequest req) {
        HttpSession session = req.getSession(); Object value = session.getAttribute("cart");
        if (value == null) { List<CartItem> cart = new ArrayList<>(); session.setAttribute("cart", cart); return cart; }
        return (List<CartItem>) value;
    }
    private void message(HttpServletRequest req, HttpServletResponse resp, String text) throws IOException { resp.sendRedirect(req.getContextPath() + "/gio-hang?success=" + java.net.URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8)); }
}
