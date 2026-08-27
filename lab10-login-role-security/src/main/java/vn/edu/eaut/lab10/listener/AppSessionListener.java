package vn.edu.eaut.lab10.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.ActivityLogService;

@WebListener
public class AppSessionListener implements HttpSessionListener {

    private final ActivityLogService logService = new ActivityLogService();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Track session creation if needed
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            User user = (User) se.getSession().getAttribute("currentUser");
            if (user != null) {
                logService.log(user.getEmail(), user.getRole().name(), "SESSION_TIMEOUT",
                        "Phiên làm việc đã kết thúc (Timeout hoặc Logout)", "N/A", "SUCCESS");
            }
        } catch (Exception ignored) {
        }
    }
}
