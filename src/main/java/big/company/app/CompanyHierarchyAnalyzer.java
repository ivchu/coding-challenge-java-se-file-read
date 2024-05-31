package big.company.app;

import big.company.app.dto.CompanyReport;
import big.company.app.dto.Employee;
import big.company.app.exception.FileIOException;
import big.company.app.exception.LineReadException;
import big.company.app.factory.impl.DefaultEmployeeReportFactory;
import big.company.app.service.CompanyStructureAnalyzer;
import big.company.app.service.EmployeeFileReader;
import big.company.app.service.impl.ConsoleEmployeeAnalysisResultPrinter;
import big.company.app.service.impl.CsvEmployeeFileReader;
import big.company.app.service.impl.DefaultCompanyStructureAnalyzer;
import big.company.app.strategy.impl.ReportingLineEmployeeAnalysisStrategy;
import big.company.app.strategy.impl.SalaryEmployeeAnalysisStrategy;

import java.util.Map;
import java.util.logging.Logger;

public class CompanyHierarchyAnalyzer {
    public static EmployeeFileReader employeeFileReader = new CsvEmployeeFileReader();
    public static CompanyStructureAnalyzer companyStructureAnalyzer = new DefaultCompanyStructureAnalyzer(
            new ReportingLineEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory()),
            new SalaryEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory())
    );
    public static ConsoleEmployeeAnalysisResultPrinter consoleEmployeeAnalysisResultPrinter =
            new ConsoleEmployeeAnalysisResultPrinter();
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        // TODO check for arguments and print error if needed
        // TODO add test for this class
        // TODO add readme and assumptions
//        String pathToFile = args[0];
//        System.out.println("Hello World! " + pathToFile);
        String pathToFile = "/Users/Ivan_Chuvakhin/Documents/Study/big-company-app/src/test/testData/example.csv";

        try {
            Map<String, Employee> employeeMap = employeeFileReader.readEmployeesFile(pathToFile);
            CompanyReport companyReport = companyStructureAnalyzer.analyzeStructure(employeeMap);
            consoleEmployeeAnalysisResultPrinter.printReportResult(companyReport);
        } catch (FileIOException e) {
            // message shit
        } catch (LineReadException e) {
            // message shit
        } catch (NumberFormatException e) {
            // message shit
        }

    }
}
