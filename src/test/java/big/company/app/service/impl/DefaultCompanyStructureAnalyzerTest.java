package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;
import big.company.app.mocks.MockReportingLineEmployeeAnalysisStrategy;
import big.company.app.mocks.MockSalaryEmployeeAnalysisStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DefaultCompanyStructureAnalyzerTest {
    private DefaultCompanyStructureAnalyzer companyStructureAnalyzer;

    @BeforeEach
    public void setUp() {
        companyStructureAnalyzer = new DefaultCompanyStructureAnalyzer(
                new MockReportingLineEmployeeAnalysisStrategy(),
                new MockSalaryEmployeeAnalysisStrategy());
    }

    @Test
    @DisplayName("Return empty company report if employeeMap is null")
    public void shouldReturnEmptyCompanyReportForNullEmployeeMap() {
        CompanyReport companyReport = companyStructureAnalyzer.analyzeStructure(null);
        Assertions.assertTrue(companyReport.getLowerSalary().isEmpty());
        Assertions.assertTrue(companyReport.getHigherSalary().isEmpty());
        Assertions.assertTrue(companyReport.getLongReportingLine().isEmpty());
    }

    @Test
    @DisplayName("Return company report with higherSalary if employee report has highSalary as true")
    public void shouldReturnHigherSalaryIfEmployeeReportHasHighSalaryAsTrue() {
        HashMap<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("1", new Employee("1", "Carl", "Third", 6000));
        CompanyReport companyReport = companyStructureAnalyzer.analyzeStructure(employeeMap);

        Assertions.assertEquals(companyReport.getHigherSalary().size(), 1);
        Assertions.assertEquals(companyReport.getHigherSalary().getFirst().getEmployeeId(), "1");
    }

    @Test
    @DisplayName("Return company report with lowerSalary if employee report has highSalary as false")
    public void shouldReturnLowerSalaryIfEmployeeReportHasHighSalaryAsFalse() {
        HashMap<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("1", new Employee("1", "Carl", "Third", 4000));
        CompanyReport companyReport = companyStructureAnalyzer.analyzeStructure(employeeMap);

        Assertions.assertEquals(companyReport.getLowerSalary().size(), 1);
        Assertions.assertEquals(companyReport.getLowerSalary().getFirst().getEmployeeId(), "1");
    }

    @Test
    @DisplayName("Return company report with longReportingLine if salaryEmployeeAnalysisStrategy returns report")
    public void longReportingLine() {
        HashMap<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("1", new Employee("1", "Carl", "Third", 4000));
        CompanyReport companyReport = companyStructureAnalyzer.analyzeStructure(employeeMap);

        Assertions.assertEquals(companyReport.getLongReportingLine().size(), 1);
        Assertions.assertEquals(companyReport.getLongReportingLine().getFirst().getEmployeeId(), "1");
    }
}
