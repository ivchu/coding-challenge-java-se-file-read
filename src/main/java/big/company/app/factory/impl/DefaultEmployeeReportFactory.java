package big.company.app.factory.impl;

import big.company.app.dto.Employee;
import big.company.app.dto.EmployeeReport;
import big.company.app.factory.EmployeeReportFactory;

public class DefaultEmployeeReportFactory implements EmployeeReportFactory {
    @Override
    public EmployeeReport createEmployeeReport(Employee employee, boolean isSalaryHigher, double average, int hierarchyLevel) {
        EmployeeReport report = new EmployeeReport();
        report.setEmployee(employee);
        report.setSalaryHigher(isSalaryHigher);
        report.setSubordinateMedianSalary(average);
        report.setHierarchyLevel(hierarchyLevel);
        return report;
    }
}
