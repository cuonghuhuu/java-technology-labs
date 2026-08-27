package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab10.model.Sach;
import vn.edu.eaut.lab10.util.JPAUtil;

import java.util.List;

public class SachRepository {

    public List<Sach> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Sach s ORDER BY s.id ASC", Sach.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Sach findById(Integer id) {
        if (id == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Sach.class, id);
        } finally {
            em.close();
        }
    }

    public Sach findByMa(String maSach) {
        if (maSach == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Sach s WHERE LOWER(s.maSach) = LOWER(:ma)", Sach.class)
                    .setParameter("ma", maSach.trim())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public List<Sach> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            return em.createQuery("SELECT s FROM Sach s WHERE LOWER(s.maSach) LIKE :kw OR LOWER(s.tenSach) LIKE :kw OR LOWER(s.tacGia) LIKE :kw OR LOWER(s.theLoai) LIKE :kw ORDER BY s.id ASC", Sach.class)
                    .setParameter("kw", pattern)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Sach sach) {
        JPAUtil.executeInTransaction(em -> em.persist(sach));
    }

    public void update(Sach sach) {
        JPAUtil.executeInTransaction(em -> em.merge(sach));
    }

    public void delete(Integer id) {
        JPAUtil.executeInTransaction(em -> {
            Sach s = em.find(Sach.class, id);
            if (s != null) {
                em.remove(s);
            }
        });
    }

    public boolean existsByMa(String maSach, Integer excludeId) {
        if (maSach == null) return false;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(s) FROM Sach s WHERE LOWER(s.maSach) = LOWER(:ma)";
            if (excludeId != null) {
                jpql += " AND s.id != :excludeId";
            }
            TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                    .setParameter("ma", maSach.trim());
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
            return em.createQuery("SELECT COUNT(s) FROM Sach s", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
