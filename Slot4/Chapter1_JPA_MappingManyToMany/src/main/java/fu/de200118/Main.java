package fu.de200118;

import fu.de200118.dao.EmployeeDAO;
import fu.de200118.pojo.Employee;
import fu.de200118.pojo.Gender;
import fu.de200118.pojo.Project;
import fu.de200118.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        testTODO57();
        testTODO59();
    }

    public static void testTODO57() {
        EntityManagerFactory emf = JPAUtil.getEMF();

        EmployeeDAO employeeDAO = new EmployeeDAO();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // ===== TẠO 3 EMPLOYEE =====
            Employee e1 = new Employee();
            e1.setEmail("employee1@company.com");
            e1.setFullName("Employee 1");
            e1.setSalary(new BigDecimal("1500"));
            e1.setHireDate(LocalDate.of(2024, 1, 10));
            e1.setGender(Gender.MALE);
            e1.setActive(true);

            Employee e2 = new Employee();
            e2.setEmail("employee2@company.com");
            e2.setFullName("Employee 2");
            e2.setSalary(new BigDecimal("1800"));
            e2.setHireDate(LocalDate.of(2024, 2, 15));
            e2.setGender(Gender.FEMALE);
            e2.setActive(true);

            Employee e3 = new Employee();
            e3.setEmail("employee3@company.com");
            e3.setFullName("Employee 3");
            e3.setSalary(new BigDecimal("2000"));
            e3.setHireDate(LocalDate.of(2024, 3, 20));
            e3.setGender(Gender.OTHER);
            e3.setActive(true);

            // ===== TẠO 2 PROJECT =====
            Project p1 = new Project();
            p1.setProjectCode("PRJ-A");
            p1.setProjectName("Project A");

            Project p2 = new Project();
            p2.setProjectCode("PRJ-B");
            p2.setProjectName("Project B");

            // Lưu trước để lấy ID
            em.persist(e1);
            em.persist(e2);
            em.persist(e3);

            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();

            // ===== PHÂN CÔNG =====
            // NV1 → A + B
            employeeDAO.assignEmployeeToProject(
                    e1.getId(),
                    p1.getId()
            );

            employeeDAO.assignEmployeeToProject(
                    e1.getId(),
                    p2.getId()
            );

            // NV2 → B
            employeeDAO.assignEmployeeToProject(
                    e2.getId(),
                    p2.getId()
            );

            // NV3 → A
            employeeDAO.assignEmployeeToProject(
                    e3.getId(),
                    p1.getId()
            );
        }

        // ===== IN DANH SÁCH PROJECT CỦA EMPLOYEE =====
        try (EntityManager em = emf.createEntityManager()) {

            List<Employee> employees = em.createQuery(
                    "SELECT DISTINCT e " +
                            "FROM Employee e " +
                            "LEFT JOIN FETCH e.projects",
                    Employee.class
            ).getResultList();

            System.out.println("\n===== EMPLOYEE - PROJECT =====");

            for (Employee employee : employees) {

                System.out.println(
                        "\nEmployee: " + employee.getFullName()
                );

                for (Project project : employee.getProjects()) {
                    System.out.println(
                            "   - "
                                    + project.getProjectCode()
                                    + " | "
                                    + project.getProjectName()
                    );
                }
            }
        }
    }

    public static void testTODO59() {
        EntityManagerFactory emf = JPAUtil.getEMF();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            employeeDAO.unassignEmployeeFromProject(1L, 1L);
            em.getTransaction().commit();
            System.out.println("Done");
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}