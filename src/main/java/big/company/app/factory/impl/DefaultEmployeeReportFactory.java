package big.company.app.factory.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.EmployeeReportFactory;

public class DefaultEmployeeReportFactory implements EmployeeReportFactory {

    @Override
    public EmployeeReport createEmployeeReport(Employee employee, boolean salaryHigher, double average, int hierarchyLevel) {
        return new EmployeeReport(employee, average, salaryHigher, hierarchyLevel);
    }

}
