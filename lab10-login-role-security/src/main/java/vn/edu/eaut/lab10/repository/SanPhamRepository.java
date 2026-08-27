package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab10.model.SanPham;
import vn.edu.eaut.lab10.util.JPAUtil;

import java.util.List;

public class SanPhamRepository {

    public List<SanPham> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM SanPham s ORDER BY s.id ASC", SanPham.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public SanPham findById(Integer id) {
        if (id == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(SanPham.class, id);
        } finally {
            em.close();
        }
    }

    public SanPham findByMa(String maSanPham) {
        if (maSanPham == null) return null;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM SanPham s WHERE LOWER(s.maSanPham) = LOWER(:ma)", SanPham.class)
                    .setParameter("ma", maSanPham.trim())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public List<SanPham> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            return em.createQuery("SELECT s FROM SanPham s WHERE LOWER(s.maSanPham) LIKE :kw OR LOWER(s.tenSanPham) LIKE :kw OR LOWER(s.danhMuc) LIKE :kw ORDER BY s.id ASC", SanPham.class)
                    .setParameter("kw", pattern)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(SanPham sanPham) {
        JPAUtil.executeInTransaction(em -> em.persist(sanPham));
    }

    public void update(SanPham sanPham) {
        JPAUtil.executeInTransaction(em -> em.merge(sanPham));
    }

    public void delete(Integer id) {
        JPAUtil.executeInTransaction(em -> {
            SanPham sp = em.find(SanPham.class, id);
            if (sp != null) {
                em.remove(sp);
            }
        });
    }

    public boolean existsByMa(String maSanPham, Integer excludeId) {
        if (maSanPham == null) return false;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(s) FROM SanPham s WHERE LOWER(s.maSanPham) = LOWER(:ma)";
            if (excludeId != null) {
                jpql += " AND s.id != :excludeId";
            }
            TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                    .setParameter("ma", maSanPham.trim());
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
            return em.createQuery("SELECT COUNT(s) FROM SanPham s", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
