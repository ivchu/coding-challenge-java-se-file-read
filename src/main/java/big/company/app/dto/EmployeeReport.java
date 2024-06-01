package big.company.app.dto;

import java.util.Objects;

/**
 * Represents a report of an Employee,
 * including the employee, the average salary of their subordinates,
 * a flag indicating whether their salary is higher than that average,
 * and the number of extra reporting lines beyond what is considered optimal.
 */
public final class EmployeeReport {
    private final String employeeId;
    private final String employeeFirstName;
    private final String employeeLastName;
    private final int employeeSalary;
    private final double normalSalary;
    private final boolean salaryHigher;
    private final int excessReportingLine;

    public EmployeeReport(String employeeId, String employeeFirstName, String employeeLastName, int employeeSalary,
                          double normalSalary, boolean salaryHigher, int excessReportingLine) {
        this.employeeId = employeeId;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeSalary = employeeSalary;
        this.normalSalary = normalSalary;
        this.salaryHigher = salaryHigher;
        this.excessReportingLine = excessReportingLine;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public int getEmployeeSalary() {
        return employeeSalary;
    }

    public double getNormalSalary() {
        return normalSalary;
    }

    public boolean isSalaryHigher() {
        return salaryHigher;
    }

    public int getExcessReportingLine() {
        return excessReportingLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeReport report = (EmployeeReport) o;
        return employeeSalary == report.employeeSalary && Double.compare(normalSalary, report.normalSalary) == 0 && salaryHigher == report.salaryHigher && excessReportingLine == report.excessReportingLine && Objects.equals(employeeId, report.employeeId) && Objects.equals(employeeFirstName, report.employeeFirstName) && Objects.equals(employeeLastName, report.employeeLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeFirstName, employeeLastName, employeeSalary, normalSalary, salaryHigher, excessReportingLine);
    }

    @Override
    public String toString() {
        return "EmployeeReport{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeFirstName='" + employeeFirstName + '\'' +
                ", employeeLastName='" + employeeLastName + '\'' +
                ", employeeSalary=" + employeeSalary +
                ", normalSalary=" + normalSalary +
                ", salaryHigher=" + salaryHigher +
                ", excessReportingLine=" + excessReportingLine +
                '}';
    }
}