package vn.edu.eaut.lab7.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) { System.out.println("[Lab7] Ứng dụng đã khởi động."); }
    @Override
    public void contextDestroyed(ServletContextEvent event) { System.out.println("[Lab7] Ứng dụng đã dừng."); }
}
