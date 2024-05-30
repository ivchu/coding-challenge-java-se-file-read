package big.company.app.dto;

public class EmployeeReport {
    private Employee employee;
    private double subordinateMedianSalary;
    private boolean salaryHigher;
    private int hierarchyLevel;

    public EmployeeReport() {}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean getSalaryHigher() {
        return salaryHigher;
    }

    public void setSalaryHigher(boolean salaryHigher) {
        this.salaryHigher = salaryHigher;
    }

    public double getSubordinateMedianSalary() {
        return subordinateMedianSalary;
    }

    public void setSubordinateMedianSalary(double subordinateMedianSalary) {
        this.subordinateMedianSalary = subordinateMedianSalary;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    @Override
    public String toString() {
        return "EmployeeReport{" +
                "employee=" + employee +
                ", subordinateMedianSalary=" + subordinateMedianSalary +
                ", salaryHigher=" + salaryHigher +
                ", hierarchyLevel=" + hierarchyLevel +
                '}';
    }
}
