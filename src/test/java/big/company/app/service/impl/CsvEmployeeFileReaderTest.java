package big.company.app.service.impl;

import big.company.app.dto.Employee;
import big.company.app.exception.FileReadException;
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
        return Stream.of(null, "", " ");  // For two tests with null parameter
    }

    @BeforeEach
    public void setUp() {
        csvEmployeeFileReader = new CsvEmployeeFileReader();
    }

    @Test
    @DisplayName("Return map of Employees if file is valid")
    public void testReadEmployeesFileValidCsv() {
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
    @DisplayName("")
    public void testReadEmployeesFileInvalidCsv() {
        Assertions.fail();
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

        Exception exception = Assertions.assertThrows(FileReadException.class,
                () -> csvEmployeeFileReader.readEmployeesFile(filePath));

        Assertions.assertTrue(exception.getMessage().contains("cannot find file for given URL"));
    }

    @Test
    @DisplayName("Return map of employees with employees populated with populated subordinates with correct managerId")
    public void testPopulateSubordinatesValid() {
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
    public void testShouldReturnEmptyMapIfFilePathIsNullEmptyBlank(String parameter) {
        Map<String, Employee> employeeMap = csvEmployeeFileReader.readEmployeesFile(parameter);
        Assertions.assertTrue(employeeMap.isEmpty());

    }
}
