package big.company.app.strategy.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import big.company.app.strategy.EmployeeAnalysisStrategy;

import java.util.Map;
import java.util.Optional;

public class ReportingLineEmployeeAnalysisStrategy implements EmployeeAnalysisStrategy {
    private final DefaultEmployeeReportFactory employeeReportFactory;

    public ReportingLineEmployeeAnalysisStrategy(DefaultEmployeeReportFactory employeeReportFactory) {
        this.employeeReportFactory = employeeReportFactory;
    }

    @Override
    public Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap) {
        int levels = 0;
        String managerId = employee.getManagerId();
        while (managerId != null) {
            Employee manager = employeeMap.get(managerId);
            managerId = manager.getManagerId();
            levels++;
        }

        if (levels > 4) {
            return Optional.of(employeeReportFactory.createEmployeeReport(employee, false, 0, levels));
        } else {
            return Optional.empty();
        }
    }
}
