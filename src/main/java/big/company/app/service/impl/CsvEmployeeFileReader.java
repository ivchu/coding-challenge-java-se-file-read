package big.company.app.service.impl;

import big.company.app.dto.Employee;
import big.company.app.exception.FileReadException;
import big.company.app.service.EmployeeFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvEmployeeFileReader implements EmployeeFileReader {
    public static final int FIRST_LINE = 1;
    public static final String COMMA_SPLITERATOR = ",";
    public static final int LINE_WITHOUT_MANAGER_ID_SIZE = 4;

    @Override
    public Map<String, Employee> readEmployeesFile(String filePath) {

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            // no requirements regarding lines order so in order to simplify, fist read all then create tree of employees
            // can be optimized in order to iterate once
            // split into private methods
            // add some validations?

            Map<String, Employee> employeeMap = readEmployees(lines);
            populateSubordinates(employeeMap);

            return employeeMap;
        } catch (IOException e) {
            // do smth nice, file can be used by name if ran is same folder
            throw new FileReadException("cannot find file for given URL " + filePath + " please provide full path to file", e);
        }
    }

    private Map<String, Employee> readEmployees(Stream<String> lines) {
        return lines
                .skip(FIRST_LINE)
                .map(line -> Arrays.asList(line.split(COMMA_SPLITERATOR)))
                .map(this::createEmployee)
                .collect(Collectors.toMap(Employee::getId, employee -> employee));
    }

    private Employee createEmployee(List<String> lineList) {
        if (lineList.size() == LINE_WITHOUT_MANAGER_ID_SIZE) {
            return new Employee(lineList.getFirst(), lineList.get(1), lineList.get(2), Integer.parseInt(lineList.get(3)));
        }
        return new Employee(lineList.getFirst(), lineList.get(1), lineList.get(2), Integer.parseInt(lineList.get(3)), lineList.get(4));
    }

    private void populateSubordinates(Map<String, Employee> employeeMap) {
        employeeMap.forEach((employeeId, employee) -> {
            if (employee.getManagerId() != null) {
                Employee manager = employeeMap.get(employee.getManagerId());
                if (manager != null) {
                    manager.getSubordinates().add(employee);
                }
            }
        });
    }

}
