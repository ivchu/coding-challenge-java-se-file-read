package big.company.app.strategy.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import big.company.app.strategy.EmployeeAnalysisStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalaryEmployeeAnalysisStrategy implements EmployeeAnalysisStrategy {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final DefaultEmployeeReportFactory employeeReportFactory;
    private record AverageAndDifference(BigDecimal average, BigDecimal difference) {}

    public SalaryEmployeeAnalysisStrategy(DefaultEmployeeReportFactory employeeReportFactory) {
        this.employeeReportFactory = employeeReportFactory;
    }

    @Override
    public Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap) {
        if (employee == null || employeeMap == null) {
            log.log(Level.CONFIG, "missing parameters for analyze method");
            return Optional.empty();
        }
        List<Employee> subordinates = employee.getSubordinates();
        if (subordinates.isEmpty()) {
            log.log(Level.CONFIG, "Empty subordinates for salary comparison logic, employee: " + employee.getId());
            return Optional.empty();
        }

        AverageAndDifference averageAndDifference = calculateAverageAndDifference(employee, subordinates);

        if (averageAndDifference.difference().doubleValue() < 20) {
            BigDecimal expectedSalary = averageAndDifference.average().multiply(new BigDecimal("1.2"));
            EmployeeReport report = employeeReportFactory
                    .createEmployeeReport(employee, false, expectedSalary.doubleValue(), 0);
            return Optional.of(report);
        }
        if (averageAndDifference.difference().doubleValue() > 50) {
            BigDecimal expectedSalary = averageAndDifference.average().multiply(new BigDecimal("1.5"));
            EmployeeReport report = employeeReportFactory
                    .createEmployeeReport(employee, true, expectedSalary.doubleValue(), 0);
            return Optional.of(report);
        }

        return Optional.empty();
    }

    private AverageAndDifference calculateAverageAndDifference(Employee employee, List<Employee> subordinates) {
        BigDecimal average = subordinates.stream()
                .map(Employee::getSalary)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(subordinates.size()), 4, RoundingMode.HALF_UP);

        BigDecimal salary = BigDecimal.valueOf(employee.getSalary());
        BigDecimal difference = salary.subtract(average);
        BigDecimal percentDiff = difference.divide(average, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        return new AverageAndDifference(average, percentDiff);
    }
}
