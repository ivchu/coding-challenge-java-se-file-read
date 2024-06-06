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
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyHierarchyAnalyzer {
    private static final Logger log = Logger.getLogger("big.company.app.CompanyHierarchyAnalyzer");
    private static final EmployeeFileReader employeeFileReader = new CsvEmployeeFileReader();
    private static final CompanyStructureAnalyzer companyStructureAnalyzer = new DefaultCompanyStructureAnalyzer(
            new ReportingLineEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory()),
            new SalaryEmployeeAnalysisStrategy(new DefaultEmployeeReportFactory())
    );
    private static final ConsoleEmployeeAnalysisResultPrinter consoleEmployeeAnalysisResultPrinter =
            new ConsoleEmployeeAnalysisResultPrinter();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide argument for program, " +
                    "it should contain full or relative path to csv file with employees");
            log.log(Level.CONFIG, "program argument is missing");
            return;
        }
        String pathToFile = args[0];

        try {
            Map<String, Employee> employeeMap = employeeFileReader.readEmployeesFile(pathToFile);
            CompanyReport companyReport = companyStructureAnalyzer.analyzeStructure(employeeMap);
            consoleEmployeeAnalysisResultPrinter.printReportResult(companyReport);
        } catch (FileIOException e) {
            System.out.println("Problems with file read, please recheck file name " +
                    "and full or relative path to file, and try again");
            log.log(Level.CONFIG, "file read failed, failed to read employee file", e);
        } catch (LineReadException e) {
            System.out.println("Problems with data format, please recheck file and make sure " +
                    "that all employees have all required fields, and try again");
            log.log(Level.CONFIG, "file read failed, problems during employee line read", e);
        } catch (NumberFormatException e) {
            System.out.println("Problems with data format, please recheck file and make sure " +
                    "that all employees have all required fields and salary is an integer, and try again");
            log.log(Level.CONFIG, "file read failed, not correct number format provided", e);
        }

    }
}
