package big.company.app.service.impl;

import big.company.app.dto.Employee;
import big.company.app.exception.FileIOException;
import big.company.app.exception.LineReadException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

public class CsvEmployeeFileReaderTest {
    private CsvEmployeeFileReader csvEmployeeFileReader;

    static Stream<String> parameterProvider() {
        return Stream.of(null, "", " ");
    }

    @BeforeEach
    public void setUp() {
        csvEmployeeFileReader = new CsvEmployeeFileReader();
    }

    @Test
    @DisplayName("Return map of Employees if file is valid")
    public void shouldReadEmployeesFileValidCsv() {
        String filePath = "src/test/testData/validEmployees.csv";

        Map<String, Employee> employeeMap;
        employeeMap = csvEmployeeFileReader.readEmployeesFile(filePath);

        Assertions.assertEquals(5, employeeMap.size());

        Employee employee = employeeMap.get("1");
        Assertions.assertNotNull(employee, "Employee with id 1 should exist");
        Assertions.assertEquals("John", employee.getFirstName());
        Assertions.assertEquals("Doe", employee.getLastName());
        Assertions.assertEquals(5000, employee.getSalary());
        Assertions.assertNull(employee.getManagerId());
        Assertions.assertEquals(2, employee.getSubordinates().size());
    }

    @Test
    @DisplayName("Throw LineReadException if csv lacks data in the lines")
    public void shouldThrowExceptionIfCscLineIsTooShort() {
        String filePath = "src/test/testData/notValidEmployees.csv";

        Exception exception = Assertions.assertThrows(LineReadException.class,
                () -> csvEmployeeFileReader.readEmployeesFile(filePath)
        );

        Assertions.assertTrue(
                exception.getMessage().contains("Line is missing info or line delimiter is wrong comma expected")
        );
    }

    @Test
    @DisplayName("Throw NumberFormatException if csv has invalid number format for salary")
    public void shouldThrowNumberFormatExceptionIfSalaryInIncorrectFormat() {
        String filePath = "src/test/testData/notValidSalary.csv";

        Assertions.assertThrows(NumberFormatException.class,
                () -> csvEmployeeFileReader.readEmployeesFile(filePath)
        );

    }

    @Test
    @DisplayName("Return empty map if given csv is empty")
    public void testReadEmployeesFileEmptyCsv() {
        String filePath = "src/test/testData/empty.csv";
        Map<String, Employee> employeeMap;

        employeeMap = csvEmployeeFileReader.readEmployeesFile(filePath);

        Assertions.assertTrue(employeeMap.isEmpty(), "Expected empty map for empty csv file");
    }

    @Test
    @DisplayName("Throws exception if provided filePath is wrong")
    public void testReadEmployeesFileMissingCsv() {
        String filePath = "src/test/testData/nonExistentFile.csv";

        Exception exception = Assertions.assertThrows(FileIOException.class,
                () -> csvEmployeeFileReader.readEmployeesFile(filePath));

        Assertions.assertTrue(exception.getMessage().contains("Cannot find file for given URL"));
    }

    @Test
    @DisplayName("Return map of employees with employees populated with populated subordinates with correct managerId")
    public void shouldPopulateSubordinatesValid() {
        String filePath = "src/test/testData/validEmployees.csv";

        Map<String, Employee> employeeMap = csvEmployeeFileReader.readEmployeesFile(filePath);

        Employee CEO = employeeMap.get("1");
        Assertions.assertEquals(2, CEO.getSubordinates().size());
        Assertions.assertTrue(CEO.getSubordinates().stream()
                .allMatch(employee -> employee.getManagerId().equals("1")));
        Employee manager = employeeMap.get("2");
        Assertions.assertTrue(manager.getSubordinates().stream()
                .allMatch(employee -> employee.getManagerId().equals("2")));
        Assertions.assertEquals(2, manager.getSubordinates().size());
    }

    @ParameterizedTest(name = "{index} => parameter={0}")
    @MethodSource("parameterProvider")
    public void shouldReturnEmptyMapIfFilePathIsNullEmptyBlank(String parameter) {
        Map<String, Employee> employeeMap = csvEmployeeFileReader.readEmployeesFile(parameter);
        Assertions.assertTrue(employeeMap.isEmpty());
    }
}
