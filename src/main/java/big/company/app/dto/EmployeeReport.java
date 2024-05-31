package big.company.app.dto;

/**
 * Represents a report of an Employee,
 * including the employee, the average salary of their subordinates,
 * a flag indicating whether their salary is higher than that average,
 * and the number of extra reporting lines beyond what is considered optimal.
 */
public record EmployeeReport(Employee employee,
                             double subordinateAverageSalary,
                             boolean salaryHigher,
                             int excessReportingLine) {
}