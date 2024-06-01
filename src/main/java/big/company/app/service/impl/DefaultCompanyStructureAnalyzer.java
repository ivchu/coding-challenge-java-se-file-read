package big.company.app.service.impl;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;
import big.company.app.service.CompanyStructureAnalyzer;
import big.company.app.strategy.EmployeeAnalysisStrategy;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultCompanyStructureAnalyzer implements CompanyStructureAnalyzer {
    private final EmployeeAnalysisStrategy reportingLineEmployeeAnalysisStrategy;
    private final EmployeeAnalysisStrategy salaryEmployeeAnalysisStrategy;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public DefaultCompanyStructureAnalyzer(EmployeeAnalysisStrategy reportingLineEmployeeAnalysisStrategy,
                                           EmployeeAnalysisStrategy salaryEmployeeAnalysisStrategy) {
        this.reportingLineEmployeeAnalysisStrategy = reportingLineEmployeeAnalysisStrategy;
        this.salaryEmployeeAnalysisStrategy = salaryEmployeeAnalysisStrategy;
    }

    @Override
    public CompanyReport analyzeStructure(Map<String, Employee> employeeMap) {
        log.log(Level.CONFIG, "creating company report");
        CompanyReport companyReport = new CompanyReport();
        if (employeeMap == null) {
            log.log(Level.CONFIG, "employeeMap is empty");
            return companyReport;
        }

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

        log.log(Level.CONFIG, "company report is created");
        return companyReport;
    }

}
