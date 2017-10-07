package com.edu.acme.exception;

public class TooLongMessageException extends InvalidMessageException {
    public TooLongMessageException(String message) {
        super(message);
    }

    public TooLongMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
