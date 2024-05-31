package big.company.app.factory;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;

/**
 * This interface represents a factory for creating an {@code EmployeeReport} instances.
 */
public interface EmployeeReportFactory {

    /**
     * Creates an {@link EmployeeReport} instance with the given parameters.
     *
     * @param employee      The {@link Employee} object for which the EmployeeReport is to be created.
     * @param salaryHigher  A boolean flag indicating if the Employee's salary is higher than average.
     * @param average       The average salary for comparison.
     * @param hierarchyLevel The level of hierarchy of the Employee in the company.
     * @return The created EmployeeReport instance.
     */
    EmployeeReport createEmployeeReport(Employee employee,
                                        boolean salaryHigher,
                                        double average,
                                        int hierarchyLevel);
}
