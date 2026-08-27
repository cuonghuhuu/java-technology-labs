package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) {
        filterConfig.getServletContext().log("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        if (requiresAdmin(request, path) && !"ADMIN".equals(session.getAttribute("role"))) {
            request.getRequestDispatcher("/403.jsp").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        return path.equals("/") || path.equals("/index.jsp") || path.equals("/hello")
                || path.equals("/login") || path.equals("/login.jsp") || path.equals("/403.jsp")
                || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")
                || path.equals("/favicon.ico");
    }

    private boolean requiresAdmin(HttpServletRequest request, String path) {
        if (path.equals("/student-form.jsp")) return true;
        if (!path.equals("/students")) return false;
        if (!"GET".equalsIgnoreCase(request.getMethod())) return true;
        String action = request.getParameter("action");
        return "new".equals(action) || "edit".equals(action) || "delete".equals(action);
    }
}
