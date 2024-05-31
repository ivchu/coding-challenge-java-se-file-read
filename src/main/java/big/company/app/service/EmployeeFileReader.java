package big.company.app.service;

import big.company.app.dto.Employee;

import java.util.Map;

/**
 * This interface describes a reader that reads employee data from a file.
 */
public interface EmployeeFileReader {

    /**
     * Reads employee details from a file provided and returns a map where the key is
     * the employee id and the value is the corresponding {@link Employee} object.
     *
     * @param filePath The path of the file to be read.
     * @return A {@code Map} of employee Ids to their respective {@link Employee} objects.
     */
    Map<String, Employee> readEmployeesFile(String filePath);
}