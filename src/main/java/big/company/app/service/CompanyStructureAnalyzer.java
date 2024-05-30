package big.company.app.service;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;

import java.util.Map;

public interface CompanyStructureAnalyzer {

    CompanyReport analyzeStructure(Map<String, Employee> employeeMap);

}
