package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab6.store.StudentStore;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        StudentStore store = StudentStore.getInstance();
        store.initializeSampleData();
        sce.getServletContext().setAttribute("studentStore", store);
        sce.getServletContext().log("Ung dung Lab 6 da khoi dong");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("So luong sinh vien khi dung ung dung: " + StudentStore.getInstance().count());
        sce.getServletContext().log("Ung dung Lab 6 da dung");
    }
}
