package big.company.app.strategy.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReportingLineEmployeeAnalysisStrategyTest {
    private ReportingLineEmployeeAnalysisStrategy reportingLineEmployeeAnalysisStrategy;

    @BeforeEach
    public void setUp() {
        reportingLineEmployeeAnalysisStrategy =
                new ReportingLineEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory());
    }

    @Test
    @DisplayName("Return Optional.empty() if employee param is null ")
    public void shouldReturnEmptyIfEmployeeIsNull() {
        Optional<EmployeeReport> employeeReport =
                reportingLineEmployeeAnalysisStrategy.analyzeEmployee(null, new HashMap<>());
        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional.empty() if employeeMap param is null ")
    public void shouldReturnEmptyIfEmployeeMapIsNull() {
        Employee firstLevelManager = new Employee("1", "Carl", "Third", 0, "0");
        Optional<EmployeeReport> employeeReport =
                reportingLineEmployeeAnalysisStrategy.analyzeEmployee(firstLevelManager, null);
        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional.empty() if employee has less then 4 levels to CEO ")
    public void shouldReturnEmptyIfEmployeeHasLessThanFourLevels() {
        Employee CEO = new Employee("0", "Carl", "Third", 0);
        Employee firstLevelManager = new Employee("1", "Carl", "Third", 0, "0");
        CEO.getSubordinates().add(firstLevelManager);
        Map<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("0", CEO);
        employeeMap.put("1", firstLevelManager);
        Optional<EmployeeReport> employeeReport =
                reportingLineEmployeeAnalysisStrategy.analyzeEmployee(firstLevelManager, employeeMap);

        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional.empty() if employee has exactly 4 levels to CEO ")
    public void shouldReturnEmptyIfEmployeeHasExactlyFourLevels() {
        Employee CEO = new Employee("0", "Carl", "Third", 0);
        Employee firstLevelManager = new Employee("1", "Carl", "Third", 0, "0");
        Employee secondLevelManager = new Employee("2", "Carl", "Third", 0, "1");
        Employee thirdLevelManager = new Employee("3", "Carl", "Third", 0, "2");
        Employee forthLevelManager = new Employee("4", "Carl", "Third", 0, "3");

        Map<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("0", CEO);
        employeeMap.put("1", firstLevelManager);
        employeeMap.put("2", secondLevelManager);
        employeeMap.put("3", thirdLevelManager);
        employeeMap.put("4", forthLevelManager);

        Optional<EmployeeReport> employeeReport =
                reportingLineEmployeeAnalysisStrategy.analyzeEmployee(forthLevelManager, employeeMap);

        Assertions.assertTrue(employeeReport.isEmpty());
    }

    @Test
    @DisplayName("Return Optional with report and levels inside if employee has more then 4 levels to CEO ")
    public void shouldReturnEmployeeReportWithLevelsIfEmployeeHasMoreThanFourLevels() {
        Employee CEO = new Employee("0", "Carl", "Third", 0);
        Employee firstLevelManager = new Employee("1", "Carl", "Third", 0, "0");
        Employee secondLevelManager = new Employee("2", "Carl", "Third", 0, "1");
        Employee thirdLevelManager = new Employee("3", "Carl", "Third", 0, "2");
        Employee forthLevelManager = new Employee("4", "Carl", "Third", 0, "3");
        Employee fifthLevelManager = new Employee("5", "Carl", "Third", 0, "4");

        Map<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("0", CEO);
        employeeMap.put("1", firstLevelManager);
        employeeMap.put("2", secondLevelManager);
        employeeMap.put("3", thirdLevelManager);
        employeeMap.put("4", forthLevelManager);
        employeeMap.put("5", fifthLevelManager);

        Optional<EmployeeReport> employeeReport =
                reportingLineEmployeeAnalysisStrategy.analyzeEmployee(fifthLevelManager, employeeMap);

        Assertions.assertFalse(employeeReport.isEmpty());
        EmployeeReport report = employeeReport.get();
        Assertions.assertEquals(report.excessReportingLine(), 1);
        Assertions.assertEquals(report.employee().getId(), "5");
    }

    @Test
    @DisplayName("Return Optional with report and level as -1 if employee has himself in manager hierarchy")
    public void shouldReturnEmployeeReportWithNegativeLevelsIfEmployeeHasItselfInHierarchy() {
        Employee CEO = new Employee("0", "Carl", "Third", 0);
        Employee firstLevelManager = new Employee("1", "Carl", "Third", 0, "1");
        Map<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("0", CEO);
        employeeMap.put("1", firstLevelManager);
        Optional<EmployeeReport> employeeReport =
                reportingLineEmployeeAnalysisStrategy.analyzeEmployee(firstLevelManager, employeeMap);

        Assertions.assertFalse(employeeReport.isEmpty());
        EmployeeReport report = employeeReport.get();
        Assertions.assertEquals(report.employee().getId(), "1");
        Assertions.assertEquals(report.excessReportingLine(), -1);
    }
}
