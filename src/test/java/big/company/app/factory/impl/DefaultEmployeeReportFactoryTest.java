package big.company.app.factory.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultEmployeeReportFactoryTest {
    private DefaultEmployeeReportFactory defaultEmployeeReportFactory;

    @BeforeEach
    public void setUp() {
        defaultEmployeeReportFactory = new DefaultEmployeeReportFactory();
    }

    @Test
    @DisplayName("Return employee Report with provided fields")
    public void testCreateGetEmployeeReport() {
        Employee employee = new Employee("1", "Test", "Employee", 1000, "0");
        boolean salaryHigher = false;
        double average = 200.0;
        int hierarchyLevel = 3;

        EmployeeReport report = defaultEmployeeReportFactory.createEmployeeReport(employee, salaryHigher, average, hierarchyLevel);

        Assertions.assertEquals(employee.getId(), report.getEmployeeId());
        Assertions.assertEquals(salaryHigher, report.isSalaryHigher());
        Assertions.assertEquals(average, report.getNormalSalary());
        Assertions.assertEquals(hierarchyLevel, report.getExcessReportingLine());
    }

}
