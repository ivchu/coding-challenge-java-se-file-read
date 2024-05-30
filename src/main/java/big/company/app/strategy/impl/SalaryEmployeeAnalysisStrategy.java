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

    public SalaryEmployeeAnalysisStrategy(DefaultEmployeeReportFactory employeeReportFactory) {
        this.employeeReportFactory = employeeReportFactory;
    }

    @Override
    public Optional<EmployeeReport> analyzeEmployee(Employee employee, Map<String, Employee> employeeMap) {
        List<Employee> subordinates = employee.getSubordinates();
        if (subordinates.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal average = subordinates.stream()
                .map(Employee::getSalary)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(subordinates.size()), 2, RoundingMode.HALF_UP);

        BigDecimal salary = BigDecimal.valueOf(employee.getSalary());
        BigDecimal difference = salary.subtract(average);
        BigDecimal percentDiff = difference.divide(average, 3, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        double percent = percentDiff.doubleValue();

        if (percent <= 20) {
            EmployeeReport report = employeeReportFactory
                    .createEmployeeReport(employee, false, average.doubleValue(), 0);
            return Optional.of(report);
        }
        if (percent >= 50) {
            EmployeeReport report = employeeReportFactory
                    .createEmployeeReport(employee, true, average.doubleValue(), 0);
            return Optional.of(report);
        }
        return Optional.empty();
    }
}
