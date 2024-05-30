package big.company.app.strategy;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;

import java.util.Map;
import java.util.Optional;

public interface EmployeeAnalysisStrategy {
    Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap);
}
