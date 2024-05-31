package big.company.app.exception;

/**
 * This class represents a custom exception that should be thrown when a file read operation fails.
 * It extends {@link RuntimeException} meaning it's an unchecked exception.
 */
public class FileIOException extends RuntimeException {
    public FileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
