package vn.edu.eaut.lab10.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;

import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/admin/*", "/staff/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String path = request.getRequestURI();

        // 1. /admin/* requires ADMIN role
        if (path.contains("/admin/")) {
            if (user.getRole() != Role.ADMIN) {
                response.sendRedirect(request.getContextPath() + "/error/403.jsp");
                return;
            }
        }

        // 2. /staff/* requires ADMIN or STAFF role
        if (path.contains("/staff/")) {
            if (user.getRole() != Role.ADMIN && user.getRole() != Role.STAFF) {
                response.sendRedirect(request.getContextPath() + "/error/403.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
