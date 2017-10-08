package com.edu.acme.exception;

/**
 * Выбрасывается в случаях, когда сообшение, отправляемое серверу, слишком длинное
 */
public class TooLongMessageException extends InvalidMessageException {
    public TooLongMessageException(String message) {
        super(message);
    }

    public TooLongMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
