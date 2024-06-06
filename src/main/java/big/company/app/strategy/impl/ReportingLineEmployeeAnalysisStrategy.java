package big.company.app.strategy.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import big.company.app.strategy.EmployeeAnalysisStrategy;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportingLineEmployeeAnalysisStrategy implements EmployeeAnalysisStrategy {
    private static final int MAXIMUM_MANAGERS_TO_CEO = 4;
    private static final int INITIAL_LEVELS_SIZE = 0;
    private static final int CIRCULAR_DEPENDENCY = -1;
    private final DefaultEmployeeReportFactory employeeReportFactory;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public ReportingLineEmployeeAnalysisStrategy(DefaultEmployeeReportFactory employeeReportFactory) {
        this.employeeReportFactory = employeeReportFactory;
    }

    @Override
    public Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap) {
        if (employee == null || employeeMap == null) {
            log.log(Level.CONFIG, "missing parameters for analyze method");
            return Optional.empty();
        }
        int levels = INITIAL_LEVELS_SIZE;
        String managerId = employee.getManagerId();
        HashSet<String> visitedManagers = new HashSet<>();

        while (managerId != null) {
            if (visitedManagers.contains(managerId)) {
                levels = CIRCULAR_DEPENDENCY;
                log.log(Level.CONFIG, "company structure has loops in structure for employee " + employee.getId());
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
        } else if (levels == CIRCULAR_DEPENDENCY) {
            return Optional.of(employeeReportFactory.createEmployeeReport(employee, false, 0, -1));
        } else {
            return Optional.empty();
        }
    }
}
