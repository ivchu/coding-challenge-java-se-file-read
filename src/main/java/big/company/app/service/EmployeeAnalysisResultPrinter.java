package big.company.app.service;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.EmployeeReport;

import java.util.List;

/**
 * This interface specifies methods for printing the results of an employee analysis.
 */
public interface EmployeeAnalysisResultPrinter {

    /**
     * Prints the results of the company report analysis.
     *
     * @param companyReport The {@link CompanyReport} containing the analysis results to print.
     */
    void printReportResult(CompanyReport companyReport);

    /**
     * Prints the results of the analysis for employees with a higher salary.
     *
     * @param employeeReports List of {@link EmployeeReport} containing the details of employees having higher salary.
     */
    void printHigherSalary(List<EmployeeReport> employeeReports);

    /**
     * Prints the results of the analysis for employees with a lower salary.
     *
     * @param employeeReports List of {@link EmployeeReport} containing the details of employees with lower salary.
     */
    void printLowerSalary(List<EmployeeReport> employeeReports);

    /**
     * Prints the results of the analysis for employees with a long reporting line.
     *
     * @param employeeReports List of {@link EmployeeReport} containing the details of employees with long reporting lines.
     */
    void printLongReportingLine(List<EmployeeReport> employeeReports);
}
