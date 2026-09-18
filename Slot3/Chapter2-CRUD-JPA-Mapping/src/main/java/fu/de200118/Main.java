package fu.de200118;

import fu.de200118.dao.DepartmentDAO;
import fu.de200118.pojo.Department;
import fu.de200118.pojo.Employee;
import fu.de200118.pojo.Gender;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Test TODO 2.8 - N+1 Query");
            System.out.println("2. Test TODO 2.9 - JOIN FETCH");
            System.out.println("3. Test TODO 2.5 - Add employee to department");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    departmentDAO.demonstrateNPlusOne();
                    break;

                case 2:
                    List<Department> departments =
                            departmentDAO.findAllWithEmployees();

                    System.out.println("===== AFTER FIX: JOIN FETCH =====");

                    for (Department department : departments) {
                        System.out.println(
                                department.getName()
                                        + " - Employees: "
                                        + department.getEmployees().size()
                        );
                    }

                    System.out.println("Before fix: 1 + N queries");
                    System.out.println("After fix : 1 query");
                    break;
                case 3: testTODO25(); break;

                case 0:
                    System.out.println("Exit.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }

    public static void testTODO25() {
        Department dept = new Department("Marketing", "Ha Noi");
        Employee emp = new Employee("test2@company.com", "Test", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());
        Employee e2 = new Employee("bc@company.com", "B", Gender.FEMALE,
                new BigDecimal("1200"), LocalDate.of(2022, 2, 1));
        Employee e3 = new Employee("cd@company.com", "C", Gender.OTHER,
                new BigDecimal("1500"), LocalDate.of(2022, 3, 1));
        dept.addEmployee(emp);
        dept.addEmployee(e2);
        dept.addEmployee(e3);

        DepartmentDAO deptDAO = new DepartmentDAO();
        deptDAO.save(dept);
        System.out.println("Đã thêm: " + dept.getName());
    }
}
