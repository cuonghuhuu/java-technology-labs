package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter("/*")
public class AccessLogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        var session = request.getSession(false);
        Object username = session == null ? null : session.getAttribute("username");
        request.getServletContext().log("[ACCESS] " + LocalDateTime.now()
                + " " + request.getMethod() + " " + request.getRequestURI()
                + " user=" + (username == null ? "anonymous" : username));
        chain.doFilter(servletRequest, servletResponse);
    }
}
