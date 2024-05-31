package big.company.app.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a comprehensive report of a company,
 * which includes lists of employees with lower salary,
 * higher salary and has long reporting lines.
 */
public class CompanyReport {
    private final List<EmployeeReport> lowerSalary;
    private final List<EmployeeReport> higherSalary;
    private final List<EmployeeReport> longReportingLine;

    /**
     * Constructs a new instance of {@link CompanyReport} with empty lists for lower salary,
     * higher salary and long reporting lines.
     */
    public CompanyReport() {
        this.lowerSalary = new ArrayList<>();
        this.higherSalary = new ArrayList<>();
        this.longReportingLine = new ArrayList<>();
    }

    public List<EmployeeReport> getLowerSalary() {
        return lowerSalary;
    }

    public List<EmployeeReport> getHigherSalary() {
        return higherSalary;
    }

    public List<EmployeeReport> getLongReportingLine() {
        return longReportingLine;
    }

    /**
     * Adds a new instance of {@link EmployeeReport}
     * to the list of the employees with lower salaries.
     * This method has no effect if the employee report is null.
     *
     * @param employeeReport the {@link EmployeeReport} to be added.
     * @return {@code true} if the {@link EmployeeReport} was added successfully,
     * {@code false} otherwise.
     */
    public boolean addLowerSalary(EmployeeReport employeeReport) {
        return lowerSalary.add(employeeReport);
    }

    /**
     * Adds a new instance of {@link EmployeeReport}
     * to the list of the employees with higher salaries.
     * This method has no effect if the employee report is null.
     *
     * @param employeeReport the {@link EmployeeReport} to be added.
     * @return {@code true} if the {@link EmployeeReport} was added successfully,
     * {@code false} otherwise.
     */
    public boolean addHigherSalary(EmployeeReport employeeReport) {
        return higherSalary.add(employeeReport);
    }

    /**
     * Adds a new instance of {@link EmployeeReport}
     * to the list of the employees with long reporting lines.
     * This method has no effect if the employee report is null.
     *
     * @param employeeReport the {@link EmployeeReport} to be added.
     * @return {@code true} if the {@link EmployeeReport} was added successfully,
     * {@code false} otherwise.
     */
    public boolean addLongReportingLine(EmployeeReport employeeReport) {
        return longReportingLine.add(employeeReport);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyReport that = (CompanyReport) o;
        return Objects.equals(lowerSalary, that.lowerSalary)
                && Objects.equals(higherSalary, that.higherSalary)
                && Objects.equals(longReportingLine, that.longReportingLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerSalary, higherSalary, longReportingLine);
    }

    @Override
    public String toString() {
        return "CompanyReport{" +
                "lowerSalary=" + lowerSalary +
                ", higherSalary=" + higherSalary +
                ", longReportingLine=" + longReportingLine +
                '}';
    }
}
