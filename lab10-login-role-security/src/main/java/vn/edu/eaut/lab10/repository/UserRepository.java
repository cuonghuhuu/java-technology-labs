package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.util.JPAUtil;

import java.util.List;

public class UserRepository {

    public User findByEmail(String email) {
        if (email == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)", User.class)
                    .setParameter("email", email.trim())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public User findById(Integer id) {
        if (id == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public List<User> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u ORDER BY u.id ASC", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<User> search(String keyword, Role role) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE 1=1");
            if (keyword != null && !keyword.trim().isEmpty()) {
                jpql.append(" AND (LOWER(u.email) LIKE :keyword OR LOWER(u.fullName) LIKE :keyword)");
            }
            if (role != null) {
                jpql.append(" AND u.role = :role");
            }
            jpql.append(" ORDER BY u.id ASC");

            TypedQuery<User> query = em.createQuery(jpql.toString(), User.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword.trim().toLowerCase() + "%");
            }
            if (role != null) {
                query.setParameter("role", role);
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void save(User user) {
        JPAUtil.executeInTransaction(em -> em.persist(user));
    }

    public void update(User user) {
        JPAUtil.executeInTransaction(em -> em.merge(user));
    }

    public void delete(Integer id) {
        JPAUtil.executeInTransaction(em -> {
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
        });
    }

    public boolean existsByEmail(String email, Integer excludeId) {
        if (email == null) return false;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE LOWER(u.email) = LOWER(:email)";
            if (excludeId != null) {
                jpql += " AND u.id != :excludeId";
            }
            TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                    .setParameter("email", email.trim());
            if (excludeId != null) {
                query.setParameter("excludeId", excludeId);
            }
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
