package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.EmployeeReport;
import big.company.app.service.EmployeeAnalysisResultPrinter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleEmployeeAnalysisResultPrinter implements EmployeeAnalysisResultPrinter {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void printReportResult(CompanyReport companyReport) {
        log.log(Level.CONFIG, "Printing companyReport");
        List<EmployeeReport> lowerSalaryEmployees = companyReport.getLowerSalary();
        printLowerSalary(lowerSalaryEmployees);

        List<EmployeeReport> higherSalaryEmployees = companyReport.getHigherSalary();
        printHigherSalary(higherSalaryEmployees);

        List<EmployeeReport> longReportingLineEmployees = companyReport.getLongReportingLine();
        printLongReportingLine(longReportingLineEmployees);
        log.log(Level.CONFIG, "companyReport is printed");
    }

    @Override
    public void printHigherSalary(List<EmployeeReport> employeeReports) {
        log.log(Level.CONFIG, "Printing Higher Salary employeeReports");
        System.out.printf("%s managers earn more than they should%s",
                employeeReports.size(),
                System.lineSeparator());

        employeeReports.forEach(report -> System.out.printf("%s %s with id %s earns more than required by %.2f%s",
                report.employee().getFirstName(),
                report.employee().getLastName(),
                report.employee().getId(),
                Math.abs(report.employee().getSalary() - report.subordinateAverageSalary()),
                System.lineSeparator()));
        log.log(Level.CONFIG, "Higher Salary employeeReports are printed");
    }

    @Override
    public void printLowerSalary(List<EmployeeReport> employeeReports) {
        log.log(Level.CONFIG, "Printing Lower Salary employeeReports");
        System.out.printf("%s managers earn less than they should%s",
                employeeReports.size(),
                System.lineSeparator());
        employeeReports.forEach(report -> System.out.printf("%s %s with id %s earns less than required by %.2f%s",
                report.employee().getFirstName(),
                report.employee().getLastName(),
                report.employee().getId(),
                Math.abs(report.employee().getSalary() - report.subordinateAverageSalary()),
                System.lineSeparator()));
        log.log(Level.CONFIG, "Lower Salary employeeReports are printed");
    }

    @Override
    public void printLongReportingLine(List<EmployeeReport> employeeReports) {
        log.log(Level.CONFIG, "Printing Long reporting line employeeReports");
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
        log.log(Level.CONFIG, "Long reporting line employeeReports are printed");
    }
}
