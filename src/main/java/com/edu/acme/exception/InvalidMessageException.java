package com.edu.acme.exception;

/**
 * Исключение, которое выбрасывается, если сообщение, готовящееся для отправки серверу, некорректно
 */
public class InvalidMessageException extends Exception {
    public InvalidMessageException(String message) {
        super(message);
    }

    public InvalidMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
