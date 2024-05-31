package big.company.app.service;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;

import java.util.Map;

/**
 * This interface represents an analyzer for company structure.
 */
public interface CompanyStructureAnalyzer {

    /**
     * Analyzes a structure of a company based on a map of employees.
     *
     * @param employeeMap The map of employees to be analyzed,
     *                    of type {@code Map<String, Employee>}, where the key
     *                    is the employee ID and the value is the corresponding {@link Employee} object.
     *
     * @return The {@code CompanyReport} containing analysis results.
     */
    CompanyReport analyzeStructure(Map<String, Employee> employeeMap);
}
