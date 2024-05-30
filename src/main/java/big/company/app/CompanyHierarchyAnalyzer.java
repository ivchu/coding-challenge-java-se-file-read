package big.company.app;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import big.company.app.service.CompanyStructureAnalyzer;
import big.company.app.service.EmployeeFileReader;
import big.company.app.service.impl.ConsoleEmployeeAnalysisResultPrinter;
import big.company.app.service.impl.CsvEmployeeFileReader;
import big.company.app.service.impl.DefaultCompanyStructureAnalyzer;
import big.company.app.strategy.impl.ReportingLineEmployeeAnalysisStrategy;
import big.company.app.strategy.impl.SalaryEmployeeAnalysisStrategy;

import java.util.Map;

public class CompanyHierarchyAnalyzer {
    // no Spring so no dependency injection
    public static EmployeeFileReader employeeFileReader = new CsvEmployeeFileReader();
    public static CompanyStructureAnalyzer companyStructureAnalyzer = new DefaultCompanyStructureAnalyzer(
            new ReportingLineEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory()),
            new SalaryEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory())
    );
    public static ConsoleEmployeeAnalysisResultPrinter consoleEmployeeAnalysisResultPrinter = new ConsoleEmployeeAnalysisResultPrinter();

    public static void main(String[] args) {
        // TODO check for arguments and print error if needed
        // TODO add logging to console and set level during execu12tion
//        String pathToFile = args[0];
//        System.out.println("Hello World! " + pathToFile);
        String pathToFile = "/Users/Ivan_Chuvakhin/Documents/Study/big-company-app/src/test/testData/example.csv";

        Map<String, Employee> employeeMap = employeeFileReader.readEmployeesFile(pathToFile);
//        employeeMap.forEach((key, value) -> System.out.println(value));
        CompanyReport companyReport = companyStructureAnalyzer.analyzeStructure(employeeMap);
        consoleEmployeeAnalysisResultPrinter.printResult(companyReport);
    }
}
