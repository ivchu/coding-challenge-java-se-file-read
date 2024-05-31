package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;

public class ConsoleEmployeeAnalysisResultPrinterTest {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ConsoleEmployeeAnalysisResultPrinter consoleEmployeeAnalysisResultPrinter;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(byteArrayOutputStream));
        consoleEmployeeAnalysisResultPrinter = new ConsoleEmployeeAnalysisResultPrinter();
    }

    @AfterEach
    public void cleanUp() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should print to console report with correct format " +
            "if higher salary employees report is not empty")
    public void shouldPrintHigherSalary() {
        Employee employee = new Employee("1", "John", "Doe", 10000, "0");
        EmployeeReport employeeReport = new EmployeeReport(employee, 5000, true, 1);

        consoleEmployeeAnalysisResultPrinter.printHigherSalary(Collections.singletonList(employeeReport));

        String expectedOutput = """
                1 managers earn more than they should
                John Doe with id 1 earns more than required by 5000.00
                """;

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print no managers earn more than they should to console " +
            "if higher salary employees report is empty")
    public void shouldPrintInfoForEmptyHigherSalary() {
        consoleEmployeeAnalysisResultPrinter.printHigherSalary(Collections.emptyList());

        String expectedOutput = "0 managers earn more than they should\n";

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print to console report with correct format if lower salary employees report is not empty")
    public void shouldPrintLowerSalary() {
        Employee employee = new Employee("1", "John", "Doe", 4000, "0");
        EmployeeReport employeeReport = new EmployeeReport(employee, 5000, true, 1);

        consoleEmployeeAnalysisResultPrinter.printLowerSalary(Collections.singletonList(employeeReport));

        String expectedOutput = """
                1 managers earn less than they should
                John Doe with id 1 earns less than required by 1000.00
                """;

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print no managers earn less than they should to console " +
            "if lower salary employees report is empty")
    public void shouldPrintInfoForEmptyLowerSalary() {
        consoleEmployeeAnalysisResultPrinter.printLowerSalary(Collections.emptyList());

        String expectedOutput = "0 managers earn less than they should\n";

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print to console report with correct format if long reporting line report is not empty")
    public void shouldPrintLongReportingLine() {
        Employee employee = new Employee("1", "John", "Doe", 4000, "0");
        EmployeeReport employeeReport = new EmployeeReport(employee, 5000, true, 5);

        consoleEmployeeAnalysisResultPrinter.printLongReportingLine(Collections.singletonList(employeeReport));

        String expectedOutput = """
                1 Employees have a reporting line which is too long
                John Doe with id 1 has more levels than required, excess levels amount is 5
                """;

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print to console report with correct format " +
            "if long reporting line contains circular dependency is not empty")
    public void shouldPrintBrokenManagerHierarchy() {
        Employee employee = new Employee("1", "John", "Doe", 4000, "0");
        EmployeeReport employeeReport = new EmployeeReport(employee, 5000, true, -1);

        consoleEmployeeAnalysisResultPrinter.printLongReportingLine(Collections.singletonList(employeeReport));

        String expectedOutput = """
                1 Employees have a reporting line which is too long
                John Doe with id 1 has broken manager hierarchy, some manager presented more then once
                """;

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print no managers have too long reporting line to console " +
            "if long reporting line report is empty")
    public void shouldPrintInfoForEmptyReportingLine() {
        consoleEmployeeAnalysisResultPrinter.printLongReportingLine(Collections.emptyList());

        String expectedOutput = "0 Employees have a reporting line which is too long\n";

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should correctly print all report results to console")
    public void shouldPrintAllReportResults() {
        Employee employee1 = new Employee("1", "John", "Doe", 4000, "0");
        Employee employee2 = new Employee("2", "Jane", "Doe", 10000, "0");

        EmployeeReport lowerSalaryReport = new EmployeeReport(employee1, 5000, false, 1);
        EmployeeReport higherSalaryReport = new EmployeeReport(employee2, 5000, true, 2);
        EmployeeReport longLineReport = new EmployeeReport(employee1, 5000, true, 5);

        CompanyReport companyReport = new CompanyReport();
        companyReport.getLowerSalary().add(lowerSalaryReport);
        companyReport.getHigherSalary().add(higherSalaryReport);
        companyReport.getLongReportingLine().add(longLineReport);

        consoleEmployeeAnalysisResultPrinter.printReportResult(companyReport);

        String expectedOutput = """
        1 managers earn less than they should
        John Doe with id 1 earns less than required by 1000.00
        1 managers earn more than they should
        Jane Doe with id 2 earns more than required by 5000.00
        1 Employees have a reporting line which is too long
        John Doe with id 1 has more levels than required, excess levels amount is 5
        """;

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

}
