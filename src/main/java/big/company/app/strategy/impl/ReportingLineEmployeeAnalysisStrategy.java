package big.company.app.strategy.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import big.company.app.strategy.EmployeeAnalysisStrategy;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class ReportingLineEmployeeAnalysisStrategy implements EmployeeAnalysisStrategy {
    public static final int MAXIMUM_MANAGERS_TO_CEO = 4;
    private final DefaultEmployeeReportFactory employeeReportFactory;

    public ReportingLineEmployeeAnalysisStrategy(DefaultEmployeeReportFactory employeeReportFactory) {
        this.employeeReportFactory = employeeReportFactory;
    }

    @Override
    public Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap) {
        if (employee == null || employeeMap == null) {
            // TODO add logs
            return Optional.empty();
        }
        int levels = 0;
        String managerId = employee.getManagerId();
        HashSet<String> visitedManagers = new HashSet<>();

        while (managerId != null) {
            if (visitedManagers.contains(managerId)) {
                levels = -1;
                break;
            }
            visitedManagers.add(managerId);
            Employee manager = employeeMap.get(managerId);
            managerId = manager.getManagerId();
            levels++;
        }

        if (levels > MAXIMUM_MANAGERS_TO_CEO) {
            return Optional.of(employeeReportFactory
                    .createEmployeeReport(employee, false, 0, levels - MAXIMUM_MANAGERS_TO_CEO));
        } else if (levels == -1) {
            return Optional.of(employeeReportFactory.createEmployeeReport(employee, false, 0, -1));
        } else {
            return Optional.empty();
        }
    }
}
