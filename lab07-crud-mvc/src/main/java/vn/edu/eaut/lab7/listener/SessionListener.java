package vn.edu.eaut.lab7.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) { System.out.println("[Lab7] Session created: " + event.getSession().getId()); }
    @Override
    public void sessionDestroyed(HttpSessionEvent event) { System.out.println("[Lab7] Session destroyed: " + event.getSession().getId()); }
}
