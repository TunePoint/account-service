package ua.tunepoint.account.exception;

public class ExternalException extends RuntimeException {

    public ExternalException(String message) {
        super(message);
    }

    public ExternalException() {
    }
}
