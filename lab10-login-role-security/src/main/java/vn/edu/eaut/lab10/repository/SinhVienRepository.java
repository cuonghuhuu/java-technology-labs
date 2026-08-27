package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.util.JPAUtil;

import java.util.List;

public class SinhVienRepository {

    public List<SinhVien> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s ORDER BY s.id ASC", SinhVien.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public SinhVien findById(Integer id) {
        if (id == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(SinhVien.class, id);
        } finally {
            em.close();
        }
    }

    public SinhVien findByMa(String maSinhVien) {
        if (maSinhVien == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s WHERE LOWER(s.maSinhVien) = LOWER(:ma)", SinhVien.class)
                    .setParameter("ma", maSinhVien.trim())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public List<SinhVien> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            return em.createQuery("SELECT s FROM SinhVien s WHERE LOWER(s.maSinhVien) LIKE :kw OR LOWER(s.hoTen) LIKE :kw OR LOWER(s.lop) LIKE :kw OR LOWER(s.email) LIKE :kw ORDER BY s.id ASC", SinhVien.class)
                    .setParameter("kw", pattern)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(SinhVien sinhVien) {
        JPAUtil.executeInTransaction(em -> em.persist(sinhVien));
    }

    public void update(SinhVien sinhVien) {
        JPAUtil.executeInTransaction(em -> em.merge(sinhVien));
    }

    public void delete(Integer id) {
        JPAUtil.executeInTransaction(em -> {
            SinhVien sv = em.find(SinhVien.class, id);
            if (sv != null) {
                em.remove(sv);
            }
        });
    }

    public boolean existsByMa(String maSinhVien, Integer excludeId) {
        if (maSinhVien == null) return false;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(s) FROM SinhVien s WHERE LOWER(s.maSinhVien) = LOWER(:ma)";
            if (excludeId != null) {
                jpql += " AND s.id != :excludeId";
            }
            TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                    .setParameter("ma", maSinhVien.trim());
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
            return em.createQuery("SELECT COUNT(s) FROM SinhVien s", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
