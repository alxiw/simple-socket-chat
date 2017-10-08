package com.edu.acme.message;

import com.edu.acme.Command;
import com.edu.acme.exception.InvalidMessageException;
import com.edu.acme.exception.TooLongMessageException;

/**
 * Класс, служащий для проверки строки на корректное сообшение
 */
public class MessageValidator implements Validator {
    private static final int MAX_MESSAGE_LENGTH = 150;
    private static final String INVALID_COMMAND_MESSAGE = "Command is unknown. Use /snd {message} to send message" +
            "/hist to receive all messages and /chid to register";
    private static final String TOO_LONG_MESSAGE = "Your message is too long.";

    /**
     * Проверяет, является ли message корректным сообщением для сервера
     *
     * @param message сообщение, которое необходимо проверить
     * @throws InvalidMessageException исключение
     */
    @Override
    public void validate(String message) throws InvalidMessageException {
        String[] messageArr = message.split("\\s+", 2);
        try {
            Command.checkCommand(messageArr[0]);
        } catch (IllegalArgumentException e){
            throw new InvalidMessageException(INVALID_COMMAND_MESSAGE, e);
        }
        if(messageArr.length > 1 && messageArr[1].length() > MAX_MESSAGE_LENGTH)
            throw new TooLongMessageException(TOO_LONG_MESSAGE);
    }
}
