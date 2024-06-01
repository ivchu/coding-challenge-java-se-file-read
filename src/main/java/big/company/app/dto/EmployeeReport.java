package big.company.app.dto;

import java.util.Objects;

/**
 * Represents a report of an Employee,
 * including the employee, the average salary of their subordinates,
 * a flag indicating whether their salary is higher than that average,
 * and the number of extra reporting lines beyond what is considered optimal.
 */
public final class EmployeeReport {
    private final Employee employee;
    private final double normalSalary;
    private final boolean salaryHigher;
    private final int excessReportingLine;

    /**
     * Constructs a new EmployeeReport with specified details.
     *
     * @param employee            the employee this report is about.
     * @param normalSalary        the standard salary the employee should have according to company policy.
     * @param salaryHigher        boolean flag indicating if the employee's salary is higher than the normal salary.
     * @param excessReportingLine the number of reporting lines higher than the acceptable limit for the employee.
     */
    public EmployeeReport(Employee employee,
                          double normalSalary,
                          boolean salaryHigher,
                          int excessReportingLine) {
        this.employee = employee;
        this.normalSalary = normalSalary;
        this.salaryHigher = salaryHigher;
        this.excessReportingLine = excessReportingLine;
    }

    public Employee getEmployee() {
        return employee;
    }

    public double getNormalSalary() {
        return normalSalary;
    }

    public boolean getSalaryHigher() {
        return salaryHigher;
    }

    public int getExcessReportingLine() {
        return excessReportingLine;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (EmployeeReport) obj;
        return Objects.equals(this.employee, that.employee) &&
                Double.doubleToLongBits(this.normalSalary) == Double.doubleToLongBits(that.normalSalary) &&
                this.salaryHigher == that.salaryHigher &&
                this.excessReportingLine == that.excessReportingLine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, normalSalary, salaryHigher, excessReportingLine);
    }

    @Override
    public String toString() {
        return "EmployeeReport[" +
                "employee=" + employee + ", " +
                "normalSalary=" + normalSalary + ", " +
                "salaryHigher=" + salaryHigher + ", " +
                "excessReportingLine=" + excessReportingLine + ']';
    }

}