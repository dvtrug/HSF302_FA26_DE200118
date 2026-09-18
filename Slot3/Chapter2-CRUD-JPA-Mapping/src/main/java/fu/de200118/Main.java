package fu.de200118;

import fu.de200118.dao.DepartmentDAO;
import fu.de200118.dao.EmployeeDAO;
import fu.de200118.pojo.Department;
import fu.de200118.pojo.Employee;
import fu.de200118.pojo.Gender;
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
//        Department dept = new Department("Marketing", "Ha Noi");
//        Employee emp = new Employee("test2@company.com", "Test", Gender.OTHER,
//                new BigDecimal("1000"), LocalDate.now());
//        Employee e2 = new Employee("bc@company.com", "B", Gender.FEMALE,
//                new BigDecimal("1200"), LocalDate.of(2022, 2, 1));
//        Employee e3 = new Employee("cd@company.com", "C", Gender.OTHER,
//                new BigDecimal("1500"), LocalDate.of(2022, 3, 1));
//        dept.addEmployee(emp);
//        dept.addEmployee(e2);
//        dept.addEmployee(e3);
//
//        DepartmentDAO deptDAO = new DepartmentDAO();
//        deptDAO.save(dept);
//        System.out.println("Đã thêm: " + dept.getName());

        EntityManagerFactory emf = JPAUtil.getEMF();
        try (EntityManager em = emf.createEntityManager()) {

            // 1 query lấy tất cả Department
            List<Department> departments = em.createQuery(
                    "SELECT d FROM Department d",
                    Department.class
            ).getResultList();

            System.out.println("===== DANH SÁCH DEPARTMENT =====");

            for (Department department : departments) {

                System.out.println(
                        "Department: " + department.getName()
                );

                // Mỗi lần truy cập employees lần đầu
                // Hibernate sẽ chạy thêm 1 query
                System.out.println(
                        "Số employee: "
                                + department.getEmployees().size()
                );
            }

        }
    }
}