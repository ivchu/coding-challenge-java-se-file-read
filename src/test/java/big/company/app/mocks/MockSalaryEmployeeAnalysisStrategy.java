package big.company.app.mocks;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.strategy.EmployeeAnalysisStrategy;

import java.util.Map;
import java.util.Optional;

public class MockSalaryEmployeeAnalysisStrategy implements EmployeeAnalysisStrategy {

    @Override
    public Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap) {
        if (employee.getSalary() > 5000) {
            return Optional.of(new EmployeeReport(employee, 4000, true, 0));
        }
        return Optional.of(new EmployeeReport(employee, 6000, false, 0));
    }

}
