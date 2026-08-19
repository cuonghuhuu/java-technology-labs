package vn.edu.eaut.lab7.filter;

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

@WebFilter(urlPatterns = {
        "/sinh-vien",
        "/sach",
        "/san-pham",
        "/lop-hoc",
        "/diem",
        "/gio-hang"
})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse resp =
                (HttpServletResponse) response;

        HttpSession session =
                req.getSession(false);

        boolean loggedIn =
                session != null
                && session.getAttribute("username") != null;

        if (!loggedIn) {

            resp.sendRedirect(
                    req.getContextPath() + "/login.jsp"
            );

            return;
        }

        chain.doFilter(request, response);
    }
}