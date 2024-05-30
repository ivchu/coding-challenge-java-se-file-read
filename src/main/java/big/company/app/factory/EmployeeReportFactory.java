package big.company.app.factory;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;

public interface EmployeeReportFactory {

    EmployeeReport createEmployeeReport(Employee employee,
                                        boolean isSalaryHigher,
                                        double average,
                                        int hierarchyLevel);

}
