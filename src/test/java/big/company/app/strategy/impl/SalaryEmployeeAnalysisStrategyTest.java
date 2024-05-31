package big.company.app.strategy.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class SalaryEmployeeAnalysisStrategyTest {
    private SalaryEmployeeAnalysisStrategy salaryEmployeeAnalysisStrategy;

    @BeforeEach
    public void setUp() {
        salaryEmployeeAnalysisStrategy = new SalaryEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory());
    }

    @Test
    @DisplayName("Return Optional.empty() if employee param is null ")
    public void shouldReturnEmptyIfEmployeeIsNull() {
        Optional<EmployeeReport> employeeReport =
                salaryEmployeeAnalysisStrategy.analyzeEmployee(null, new HashMap<>());
        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional.empty() if employeeMap param is null ")
    public void shouldReturnEmptyIfEmployeeMapIsNull() {
        Employee firstLevelManager = new Employee("1", "Carl", "Third", 0, "0");
        Optional<EmployeeReport> employeeReport =
                salaryEmployeeAnalysisStrategy.analyzeEmployee(firstLevelManager, null);
        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional.empty() if employee subordinates is empty")
    public void shouldReturnEmptyIfEmployeeHasNoSubordinates() {
        Employee firstLevelManager = new Employee("1", "Carl", "Third", 0, "0");
        Optional<EmployeeReport> employeeReport =
                salaryEmployeeAnalysisStrategy.analyzeEmployee(firstLevelManager, new HashMap<>());
        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional.empty() if salary is between 20 and 50 of subordinates average")
    public void shouldReturnEmptyIfSalaryIsInNormalRange() {
        Employee CEO = new Employee("0", "Carl", "Third", 100000);
        Employee firstManager = new Employee("1", "Carl", "Third", 70000, "0");
        Employee secondManager = new Employee("2", "Carl", "Third", 90000, "0");

        CEO.getSubordinates().addAll(Arrays.asList(firstManager, secondManager));

        Optional<EmployeeReport> employeeReport = salaryEmployeeAnalysisStrategy.analyzeEmployee(CEO, new HashMap<>());

        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Optional.empty() if salary is equal to 20 percent of subordinates average")
    public void shouldReturnEmptyIfSalaryIsEqualToLowSalaryLevel() {
        Employee CEO = new Employee("0", "Carl", "Third", 100000);
        Employee firstManager = new Employee("1", "Carl", "Third", 74500, "0");
        Employee secondManager = new Employee("2", "Carl", "Third", 92160, "0");

        CEO.getSubordinates().addAll(Arrays.asList(firstManager, secondManager));

        Optional<EmployeeReport> employeeReport = salaryEmployeeAnalysisStrategy.analyzeEmployee(CEO, new HashMap<>());

        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional with report with salaryHigher false " +
            "if salary is less than 20 percent higher then subordinates average")
    public void shouldReturnReportIfSalaryIsLowerThanExpected() {
        Employee CEO = new Employee("0", "Carl", "Third", 100000);
        Employee firstManager = new Employee("1", "Carl", "Third", 95000, "0");
        Employee secondManager = new Employee("2", "Carl", "Third", 100000, "0");

        CEO.getSubordinates().addAll(Arrays.asList(firstManager, secondManager));

        Optional<EmployeeReport> employeeReport = salaryEmployeeAnalysisStrategy.analyzeEmployee(CEO, new HashMap<>());

        Assertions.assertFalse(employeeReport.isEmpty());
        EmployeeReport report = employeeReport.get();
        Assertions.assertEquals(report.employee().getId(), "0");
        Assertions.assertFalse(report.salaryHigher());
    }

    @Test
    @DisplayName("Optional.empty() if salary is equal to 50 percent of subordinates average")
    public void shouldReturnEmptyIfSalaryIsEqualToHighSalaryLevel() {
        Employee CEO = new Employee("0", "Carl", "Third", 100010);
        Employee firstManager = new Employee("1", "Carl", "Third", 63340, "0");
        Employee secondManager = new Employee("2", "Carl", "Third", 70010, "0");

        CEO.getSubordinates().addAll(Arrays.asList(firstManager, secondManager));

        Optional<EmployeeReport> employeeReport = salaryEmployeeAnalysisStrategy.analyzeEmployee(CEO, new HashMap<>());

        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional with report with salaryHigher true " +
            "if salary is more than 50 percent higher then subordinates average")
    public void shouldReturnReportIfSalaryIsHigherThanExpected() {
        Employee CEO = new Employee("0", "Carl", "Third", 100000);
        Employee firstManager = new Employee("1", "Carl", "Third", 60000, "0");
        Employee secondManager = new Employee("2", "Carl", "Third", 70000, "0");

        CEO.getSubordinates().addAll(Arrays.asList(firstManager, secondManager));

        Optional<EmployeeReport> employeeReport = salaryEmployeeAnalysisStrategy.analyzeEmployee(CEO, new HashMap<>());

        Assertions.assertFalse(employeeReport.isEmpty());
        EmployeeReport report = employeeReport.get();
        Assertions.assertEquals(report.employee().getId(), "0");
        Assertions.assertTrue(report.salaryHigher());
    }

    @Test
    @DisplayName("Return Optional with report with correctly calculated average salary if current lower then expected")
    public void shouldReturnCorrectAverageSalaryForLowerSalary() {
        Employee CEO = new Employee("0", "Carl", "Third", 100000);
        Employee firstManager = new Employee("1", "Carl", "Third", 95000, "0");
        Employee secondManager = new Employee("2", "Carl", "Third", 85000, "0");

        CEO.getSubordinates().addAll(Arrays.asList(firstManager, secondManager));

        Optional<EmployeeReport> employeeReport = salaryEmployeeAnalysisStrategy.analyzeEmployee(CEO, new HashMap<>());

        Assertions.assertFalse(employeeReport.isEmpty());
        EmployeeReport report = employeeReport.get();
        Assertions.assertEquals(report.employee().getId(), "0");
        Assertions.assertEquals(report.normalSalary(), 108000d);
    }

    @Test
    @DisplayName("Return Optional with report with correctly calculated average salary if current higher than expected")
    public void shouldReturnCorrectAverageSalaryForHigherSalary() {
        Employee CEO = new Employee("0", "Carl", "Third", 100000);
        Employee firstManager = new Employee("1", "Carl", "Third", 60000, "0");
        Employee secondManager = new Employee("2", "Carl", "Third", 70000, "0");

        CEO.getSubordinates().addAll(Arrays.asList(firstManager, secondManager));

        Optional<EmployeeReport> employeeReport = salaryEmployeeAnalysisStrategy.analyzeEmployee(CEO, new HashMap<>());

        Assertions.assertFalse(employeeReport.isEmpty());
        EmployeeReport report = employeeReport.get();
        Assertions.assertEquals(report.employee().getId(), "0");
        Assertions.assertEquals(report.normalSalary(), 97500d);
    }
}
