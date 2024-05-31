package big.company.app.strategy;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;

import java.util.Map;
import java.util.Optional;

/**
 * This interface defines a strategy for analyzing an employee in the context of a map of employees.
 */
public interface EmployeeAnalysisStrategy {

    /**
     * Analyzes the specified employee in the context of a map of employees, and generates a report.
     *
     * @param employee     The {@link Employee} to be analyzed.
     * @param employeeMap  The map of all employees, where the key is the employee ID
     *                     and the value is the corresponding {@link Employee} object.
     * @return An {@link Optional} of {@link EmployeeReport} that contains the analysis results
     *         if the employee can be analyzed, or an empty {@link Optional} if the employee
     *         can not be analyzed according to this strategy.
     */
    Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap);
}