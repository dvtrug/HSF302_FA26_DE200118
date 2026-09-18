package fu.de200118.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String projectCode;
    @Column
    private String projectName;
    @Column
    private BigDecimal budget;
    @Column
    private LocalDate startDate;
    @Column(nullable = true)
    private LocalDate endDate;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    public Project() {
    }

    // Do not use the database-generated id in equals/hashCode because
    // it may change from null to a generated value after persistence.
    // projectCode is used as the stable business key.
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Project)) {
            return false;
        }
        Project other = (Project) obj;
        return projectCode != null && projectCode.equals(other.projectCode);
    }

    @Override
    public int hashCode() {
        return projectCode != null ? projectCode.hashCode() : 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

}
