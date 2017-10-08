package com.edu.acme.message;

import com.edu.acme.Command;

public class MessageFactory {
    public static Message createMessage(Command command, String text) {
        if (command == Command.SEND) {
            return new TextMessage(text);
        } else {
            return null;
        }
    }

    public static Message createMessage(String command, String text) {
        return createMessage(Command.get(command), text);
    }
}
