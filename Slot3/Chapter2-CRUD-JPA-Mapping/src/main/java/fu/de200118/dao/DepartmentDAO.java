package fu.de200118.dao;

import fu.de200118.pojo.Department;
import fu.de200118.pojo.Employee;
import fu.de200118.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class DepartmentDAO {
    private final EntityManagerFactory emf = JPAUtil.getEMF();
    public void save(Department department) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(department);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT d FROM Department d",
                    Department.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Department findById(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.find(Department.class, id);
        } finally {
            em.close();
        }
    }

    public Department update(Department department) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            department = em.merge(department);
            em.getTransaction().commit();
            return department;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            Department department = em.find(Department.class, id);

            if (department != null) {
                em.remove(department);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Department findDepartmentWithEmployees(Long id) {
        // Tạo EntityManager ngắn hạn cho thao tác đọc
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Department department = null;

        try {
            // Thực thi JPQL dùng JOIN FETCH theo đúng yêu cầu đề bài
            department = em.createQuery(
                            "SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id",
                            Department.class
                    )
                    .setParameter("id", id) // Đặt tham số an toàn tránh SQL Injection
                    .getSingleResult();      // Lấy ra 1 kết quả duy nhất

        } catch (NoResultException e) {
            System.out.println("Không tìm thấy Department với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // BẮT BUỘC ĐÓNG EntityManager tại đây để giải phóng kết nối
            if (em != null) {
                em.close();
            }
        }

        // Trả thực thể về (lúc này EntityManager đã đóng hoàn toàn)
        return department;
    }
}


