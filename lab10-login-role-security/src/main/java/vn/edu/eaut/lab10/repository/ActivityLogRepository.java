package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab10.model.ActivityLog;
import vn.edu.eaut.lab10.util.JPAUtil;

import java.util.List;

public class ActivityLogRepository {

    public List<ActivityLog> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM ActivityLog l ORDER BY l.timestamp DESC", ActivityLog.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ActivityLog> findRecent(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM ActivityLog l ORDER BY l.timestamp DESC", ActivityLog.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ActivityLog> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            return em.createQuery("SELECT l FROM ActivityLog l WHERE LOWER(l.userEmail) LIKE :kw OR LOWER(l.action) LIKE :kw OR LOWER(l.details) LIKE :kw ORDER BY l.timestamp DESC", ActivityLog.class)
                    .setParameter("kw", pattern)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(ActivityLog log) {
        JPAUtil.executeInTransaction(em -> em.persist(log));
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(l) FROM ActivityLog l", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
