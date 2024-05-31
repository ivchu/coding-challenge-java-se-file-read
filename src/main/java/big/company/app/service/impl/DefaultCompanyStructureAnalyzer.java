package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;
import big.company.app.service.CompanyStructureAnalyzer;
import big.company.app.strategy.EmployeeAnalysisStrategy;

import java.util.Map;

public class DefaultCompanyStructureAnalyzer implements CompanyStructureAnalyzer {
    private final EmployeeAnalysisStrategy reportingLineEmployeeAnalysisStrategy;
    private final EmployeeAnalysisStrategy salaryEmployeeAnalysisStrategy;

    public DefaultCompanyStructureAnalyzer(EmployeeAnalysisStrategy reportingLineEmployeeAnalysisStrategy,
                                           EmployeeAnalysisStrategy salaryEmployeeAnalysisStrategy) {
        this.reportingLineEmployeeAnalysisStrategy = reportingLineEmployeeAnalysisStrategy;
        this.salaryEmployeeAnalysisStrategy = salaryEmployeeAnalysisStrategy;
    }

    @Override
    public CompanyReport analyzeStructure(Map<String, Employee> employeeMap) {
        CompanyReport companyReport = new CompanyReport();
        if (employeeMap == null) {
            return companyReport;
        }

        employeeMap.forEach((string, employee) -> {
            salaryEmployeeAnalysisStrategy.analyzeEmployee(employee, employeeMap)
                    .ifPresent(employeeReport -> {
                        if (employeeReport.salaryHigher()) {
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
