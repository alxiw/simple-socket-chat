package com.edu.acme.message;

import com.edu.acme.Command;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MessageFactory {
    public static Message createMessage(Command command, String text, ObjectOutputStream out) {
        if (command == Command.SEND) {
            return new SendMessage(text);
        } else if (command == Command.REGISTER){
            return new LoginMessage(text, out);
        }
        else {
            return null;
        }
    }

    public static Message createMessage(String command, String text, ObjectOutputStream out){
        return createMessage(Command.get(command), text, out);
    }
}
