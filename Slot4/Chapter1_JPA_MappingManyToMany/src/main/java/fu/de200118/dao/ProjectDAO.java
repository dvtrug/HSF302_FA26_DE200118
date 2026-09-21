package fu.de200118.dao;

import fu.de200118.pojo.Project;
import fu.de200118.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ProjectDAO {
    private final EntityManagerFactory emf = JPAUtil.getEMF();

    public void countActiveEmployeeInProjectAndGetSumSalary(Project project) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery(
                    "SELECT p.projectName, COUNT(e), SUM(e.salary)\n" +
                    "FROM Project p JOIN p.employees e\n" +
                    "WHERE e.active = true\n" +
                    "GROUP BY p.projectName\n");

            em.merge(project);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Project p = em.find(Project.class, id);
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
