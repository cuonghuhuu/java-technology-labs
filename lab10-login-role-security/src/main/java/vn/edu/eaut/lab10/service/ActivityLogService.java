package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.ActivityLog;
import vn.edu.eaut.lab10.repository.ActivityLogRepository;

import java.util.List;

public class ActivityLogService {

    private final ActivityLogRepository repository = new ActivityLogRepository();

    public void log(String userEmail, String userRole, String action, String details, String ipAddress, String status) {
        try {
            ActivityLog log = new ActivityLog(userEmail, userRole, action, details, ipAddress, status);
            repository.save(log);
        } catch (Exception e) {
            System.err.println("Error saving activity log: " + e.getMessage());
        }
    }

    public List<ActivityLog> findAll() {
        return repository.findAll();
    }

    public List<ActivityLog> findRecent(int limit) {
        return repository.findRecent(limit);
    }

    public List<ActivityLog> search(String keyword) {
        return repository.search(keyword);
    }

    public long count() {
        return repository.count();
    }
}
