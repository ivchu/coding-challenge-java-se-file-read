package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;
import big.company.app.service.CompanyStructureAnalyzer;
import big.company.app.strategy.impl.ReportingLineEmployeeAnalysisStrategy;
import big.company.app.strategy.impl.SalaryEmployeeAnalysisStrategy;

import java.util.Map;

public class DefaultCompanyStructureAnalyzer implements CompanyStructureAnalyzer {
    private final ReportingLineEmployeeAnalysisStrategy reportingLineEmployeeAnalysisStrategy;
    private final SalaryEmployeeAnalysisStrategy salaryEmployeeAnalysisStrategy;

    public DefaultCompanyStructureAnalyzer(ReportingLineEmployeeAnalysisStrategy reportingLineEmployeeAnalysisStrategy,
                                           SalaryEmployeeAnalysisStrategy salaryEmployeeAnalysisStrategy) {
        this.reportingLineEmployeeAnalysisStrategy = reportingLineEmployeeAnalysisStrategy;
        this.salaryEmployeeAnalysisStrategy = salaryEmployeeAnalysisStrategy;
    }

    @Override
    public CompanyReport analyzeStructure(Map<String, Employee> employeeMap) {
        CompanyReport companyReport = new CompanyReport();

        employeeMap.forEach((string, employee) -> {
            salaryEmployeeAnalysisStrategy.analyzeEmployee(employee, employeeMap)
                    .ifPresent(employeeReport -> {
                        if (employeeReport.getSalaryHigher()) {
                            companyReport.addHigherSalary(employeeReport);
                        } else {
                            companyReport.addLowerSalary(employeeReport);
                        }
                    });

            reportingLineEmployeeAnalysisStrategy.analyzeEmployee(employee, employeeMap)
                    .ifPresent(companyReport::addLongReportingLine);

        });

        return companyReport;
    }

}
