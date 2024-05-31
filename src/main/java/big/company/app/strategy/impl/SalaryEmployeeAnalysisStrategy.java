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

public class SalaryEmployeeAnalysisStrategy implements EmployeeAnalysisStrategy {
    private final DefaultEmployeeReportFactory employeeReportFactory;
    private record AverageAndDifference(BigDecimal average, BigDecimal difference) {}

    public SalaryEmployeeAnalysisStrategy(DefaultEmployeeReportFactory employeeReportFactory) {
        this.employeeReportFactory = employeeReportFactory;
    }

    @Override
    public Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap) {
        if (employee == null || employeeMap == null) {
            // TODO add logs
            return Optional.empty();
        }
        List<Employee> subordinates = employee.getSubordinates();
        if (subordinates.isEmpty()) {
            return Optional.empty();
        }

        AverageAndDifference averageAndDifference = calculateAverageAndDifference(employee, subordinates);

        if (averageAndDifference.difference().doubleValue() < 20) {
            EmployeeReport report = employeeReportFactory
                    .createEmployeeReport(employee, false, averageAndDifference.average().doubleValue(), 0);
            return Optional.of(report);
        }
        if (averageAndDifference.difference().doubleValue() > 50) {
            EmployeeReport report = employeeReportFactory
                    .createEmployeeReport(employee, true, averageAndDifference.average().doubleValue(), 0);
            return Optional.of(report);
        }

        return Optional.empty();
    }

    private AverageAndDifference calculateAverageAndDifference(Employee employee, List<Employee> subordinates) {
        // TODO is accuracy is good enough?
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
