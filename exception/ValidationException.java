package project.project.exception;

public class ValidationException extends DomainException {

    public ValidationException(String message) {
        super("VALIDATION_ERROR", message);
    }

    public ValidationException(String message, Object details) {
        super("VALIDATION_ERROR", message);
    }
}
