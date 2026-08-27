package vn.edu.eaut.lab10.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/admin/*", "/staff/*", "/user/*", "/dashboard"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (currentUser == null) {
            String requestURI = request.getRequestURI();
            String queryString = request.getQueryString();
            String fullPath = requestURI + (queryString != null ? "?" + queryString : "");

            response.sendRedirect(request.getContextPath() + "/login.jsp?redirect=" +
                    URLEncoder.encode(fullPath, StandardCharsets.UTF_8));
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
