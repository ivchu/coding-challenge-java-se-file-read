package big.company.app.service;

import big.company.app.dto.Employee;

import java.util.Map;

public interface EmployeeFileReader {

    Map<String, Employee> readEmployeesFile(String filePath);

}
