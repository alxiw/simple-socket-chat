package com.github.alxiw.simplesocketchat.network;

/**
 * Класс, служащий для проверки строки сообщения на корректность
 */
public class MessageValidator {
    /**
     * Поле - максимальная длина сообщения
     */
    private static final int MAX_MESSAGE_LENGTH = 150;
    private static final String HELP_MESSAGE = "Use /snd {message} to send message, /hist to receive all messages, " +
            "/chid to register or /chroom to change room";
    private static final String UNKNOWN_COMMAND_MESSAGE = "Command is unknown. Use /snd {message} to send message " +
            "/hist to receive all messages, /chid to register or /chroom to change room";
    public static final String CAN_T_SEND_EMPTY_MESSAGE = "Can't send empty message";
    public static final String TOO_LONG_MESSAGE = "Your message is too long";

    /**
     * Проверяет, является ли message корректным сообщением для сервера
     * @param message сообщение, которое необходимо проверить
     * @return возвращает строку с описанием ошибки
     */
    public String getErrorDescription(String message) {
        String[] messageArr = message.split("\\s+", 2);
        if(message.trim().equals("/help")){
            return HELP_MESSAGE;
        }
        if (Command.get(messageArr[0]) == null) {
            return UNKNOWN_COMMAND_MESSAGE;
        }
        else if ((messageArr.length < 2 || messageArr[1].equals("") || messageArr[1].equals(" ")) && Command.get(message) == Command.SEND) {
            return CAN_T_SEND_EMPTY_MESSAGE;
        } else if (messageArr.length == 2 && messageArr[1].length() >= MAX_MESSAGE_LENGTH) {
            return TOO_LONG_MESSAGE;
        }
        return null;
    }
}