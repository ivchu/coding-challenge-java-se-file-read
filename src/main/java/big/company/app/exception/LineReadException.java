package big.company.app.exception;

/**
 * This class represents a custom exception that should be thrown when a file read operation fails.
 * It extends {@link RuntimeException} meaning it's an unchecked exception.
 */
public class LineReadException extends RuntimeException {
    public LineReadException(String message) {
        super(message);
    }
}
