package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.service.EmployeeAnalysisResultPrinter;

public class ConsoleEmployeeAnalysisResultPrinter implements EmployeeAnalysisResultPrinter {

    @Override
    public boolean printResult(CompanyReport employeeReports) {
        System.out.println("Managers earn less than they should");
        employeeReports.getLowerSalary().forEach(System.out::println);
        System.out.println("Managers earn more than they should");
        employeeReports.getHigherSalary().forEach(System.out::println);
        System.out.println("Employees have a reporting line which is too long");
        employeeReports.getLongReportingLine().forEach(System.out::println);
        return false;
    }
}
