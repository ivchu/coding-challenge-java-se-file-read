package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.EmployeeReport;
import big.company.app.service.EmployeeAnalysisResultPrinter;

import java.util.List;

public class ConsoleEmployeeAnalysisResultPrinter implements EmployeeAnalysisResultPrinter {

    @Override
    public void printReportResult(CompanyReport employeeReports) {
        List<EmployeeReport> lowerSalaryEmployees = employeeReports.getLowerSalary();
        printLowerSalary(lowerSalaryEmployees);

        List<EmployeeReport> higherSalaryEmployees = employeeReports.getHigherSalary();
        printHigherSalary(higherSalaryEmployees);

        List<EmployeeReport> longReportingLineEmployees = employeeReports.getLongReportingLine();
        printLongReportingLine(longReportingLineEmployees);
    }

    @Override
    public void printHigherSalary(List<EmployeeReport> employeeReports) {
        System.out.printf("%s managers earn more than they should%s",
                employeeReports.size(),
                System.lineSeparator());

        employeeReports.forEach(report -> System.out.printf("%s %s with id %s earns more than required by %.2f%s",
                report.employee().getFirstName(),
                report.employee().getLastName(),
                report.employee().getId(),
                Math.abs(report.employee().getSalary() - report.subordinateAverageSalary()),
                System.lineSeparator()));
    }

    @Override
    public void printLowerSalary(List<EmployeeReport> employeeReports) {
        System.out.printf("%s managers earn less than they should%s",
                employeeReports.size(),
                System.lineSeparator());
        employeeReports.forEach(report -> System.out.printf("%s %s with id %s earns less than required by %.2f%s",
                report.employee().getFirstName(),
                report.employee().getLastName(),
                report.employee().getId(),
                Math.abs(report.employee().getSalary() - report.subordinateAverageSalary()),
                System.lineSeparator()));
    }

    @Override
    public void printLongReportingLine(List<EmployeeReport> employeeReports) {
        System.out.printf("%s Employees have a reporting line which is too long%s",
                employeeReports.size(),
                System.lineSeparator());

        employeeReports.forEach(report -> {
            if (report.excessReportingLine() != -1) {
                System.out.printf("%s %s with id %s has more levels than required, excess levels amount is %d%s",
                        report.employee().getFirstName(),
                        report.employee().getLastName(),
                        report.employee().getId(),
                        report.excessReportingLine(),
                        System.lineSeparator());
            } else {
                System.out.printf("%s %s with id %s has broken manager hierarchy, some manager presented more then once%s",
                        report.employee().getFirstName(),
                        report.employee().getLastName(),
                        report.employee().getId(),
                        System.lineSeparator());
            }
        });
    }
}
