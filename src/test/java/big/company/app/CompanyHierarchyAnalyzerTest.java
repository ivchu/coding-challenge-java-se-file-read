package big.company.app;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CompanyHierarchyAnalyzerTest {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @AfterEach
    public void cleanUp() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should print error if app ran without arguments")
    public void shouldPrintErrorForEmptyArguments() {
        CompanyHierarchyAnalyzer.main(new String[]{});
        String expectedOutput = "Please provide argument for program, " +
                "it should contain full or relative path to csv file with employees\n";

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print error if app throw IO Exception")
    public void shouldPrintErrorForIOException() {
        CompanyHierarchyAnalyzer.main(new String[]{"src/test/testData/nonExistentFile.csv"});
        String expectedOutput = "Problems with file read, please recheck file name " +
                "and full or relative path to file, and try again\n";

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print error if app throw LineReadException")
    public void shouldPrintErrorForLineReadException() {
        CompanyHierarchyAnalyzer.main(new String[]{"src/test/testData/notValidEmployees.csv"});
        String expectedOutput = "Problems with data format, please recheck file and make sure " +
                "that all employees have all required fields, and try again\n";

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print error if app throw NumberFormatException")
    public void shouldPrintErrorForNumberFormatException() {
        CompanyHierarchyAnalyzer.main(new String[]{"src/test/testData/notValidSalary.csv"});
        String expectedOutput = "Problems with data format, please recheck file and make sure " +
                "that all employees have all required fields and salary is an integer, and try again\n";

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Should print result if valid file is provided")
    public void shouldPrintResultIfValidFileIsProvided() {
        CompanyHierarchyAnalyzer.main(new String[]{"src/test/testData/validEmployees.csv"});
        String expectedOutput = """
                2 managers earn less than they should
                John Doe with id 1 earns less than required by 750.00
                Jane Smith with id 2 earns less than required by 1250.00
                0 managers earn more than they should
                0 Employees have a reporting line which is too long
                """;

        Assertions.assertEquals(expectedOutput, byteArrayOutputStream.toString());
    }
}
