package big.company.app.service.impl;

import big.company.app.dto.Employee;
import big.company.app.exception.FileIOException;
import big.company.app.exception.LineReadException;
import big.company.app.service.EmployeeFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvEmployeeFileReader implements EmployeeFileReader {
    private static final int FIRST_LINE = 1;
    private static final String COMMA_DELIMITER = ",";
    private static final int LINE_WITHOUT_MANAGER_ID_SIZE = 4;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public Map<String, Employee> readEmployeesFile(String filePath) {
        log.log(Level.CONFIG, "Reading employees file for filepath " + filePath);
        if (filePath == null || filePath.isEmpty() || filePath.isBlank()) {
            log.log(Level.CONFIG, "filePath is missing, cannot read file");
            return new HashMap<>();
        }

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            Map<String, Employee> employeeMap = readEmployees(lines);
            populateSubordinates(employeeMap);

            log.log(Level.CONFIG, "File read successfully for filepath " + filePath);
            return employeeMap;
        } catch (IOException e) {
            log.log(Level.CONFIG, "Exception during reading a file for filepath " + filePath, e);
            throw new FileIOException("Cannot find file for given URL " + filePath, e);
        }
    }

    private Map<String, Employee> readEmployees(Stream<String> lines) {
        return lines
                .skip(FIRST_LINE)
                .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                .map(this::createEmployee)
                .collect(Collectors.toMap(Employee::getId, employee -> employee));
    }

    private Employee createEmployee(List<String> lineList) {
        if (lineList.size() < LINE_WITHOUT_MANAGER_ID_SIZE) {
            log.log(Level.CONFIG, "csv line has incorrect format " + String.join(",", lineList));
            throw new LineReadException("Line is missing info or line delimiter is wrong comma expected");
        }
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
