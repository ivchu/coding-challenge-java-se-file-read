package big.company.app.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyReport {
    private final List<EmployeeReport> lowerSalary;
    private final List<EmployeeReport> higherSalary;
    private final List<EmployeeReport> longReportingLine;


    public CompanyReport() {
        this.lowerSalary = new ArrayList<>();
        this.higherSalary = new ArrayList<>();
        this.longReportingLine = new ArrayList<>();
    }

    public List<EmployeeReport> getLowerSalary() {
        return lowerSalary;
    }

    public List<EmployeeReport> getHigherSalary() {
        return higherSalary;
    }

    public List<EmployeeReport> getLongReportingLine() {
        return longReportingLine;
    }

    public boolean addLowerSalary(EmployeeReport employeeReport) {
        return lowerSalary.add(employeeReport);
    }

    public boolean addHigherSalary(EmployeeReport employeeReport) {
        return higherSalary.add(employeeReport);
    }

    public boolean addLongReportingLine(EmployeeReport employeeReport) {
        return longReportingLine.add(employeeReport);
    }
}
