package big.company.app.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Employee within a company,
 * including the employee's ID, name, salary, manager ID and subordinates.
 */
public class Employee {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final int salary;
    private final String managerId;
    private final List<Employee> subordinates;

    /**
     * Constructs an {@link Employee} instance without a manager.
     * Initializes the list of subordinates as an empty list.
     *
     * @param id        the employee's id
     * @param firstName the employee's first name
     * @param lastName  the employee's last name
     * @param salary    the employee's salary
     */
    public Employee(String id, String firstName, String lastName, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = null;
        this.subordinates = new ArrayList<>();
    }

    /**
     * Constructs an {@link Employee} instance with a manager.
     * Initializes the list of subordinates as an empty list.
     *
     * @param id        the employee's id
     * @param firstName the employee's first name
     * @param lastName  the employee's last name
     * @param salary    the employee's salary
     * @param managerId the employee's manager's id
     */
    public Employee(String id, String firstName, String lastName, int salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
        this.subordinates = new ArrayList<>();
    }

    public String getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalary() {
        return salary;
    }

    public String getManagerId() {
        return managerId;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary
                && Objects.equals(id, employee.id)
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(lastName, employee.lastName)
                && Objects.equals(managerId, employee.managerId)
                && Objects.equals(subordinates, employee.subordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, salary, managerId, subordinates);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", managerId=" + managerId +
                ", subordinates=" + subordinates +
                '}';
    }
}
