package big.company.app.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
    // no specific reqs on size assume int is enough
    private String id;
    private String firstName;
    private String lastName;
    private int salary;
    private String managerId;
    private List<Employee> subordinates;

    public Employee() {
        this.subordinates = new ArrayList<>();
    }

    public Employee(String id, String firstName, String lastName, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.subordinates = new ArrayList<>();
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(salary, employee.salary) && Objects.equals(managerId, employee.managerId) && Objects.equals(subordinates, employee.subordinates);
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
