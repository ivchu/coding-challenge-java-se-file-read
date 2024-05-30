package big.company.app.exception;

public class FileReadException extends RuntimeException {
    public FileReadException() {
    }

    public FileReadException(String message) {
        super(message);
    }

    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
