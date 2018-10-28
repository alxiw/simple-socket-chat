package com.github.alxiw.simplesocketchat.network;

import java.io.ObjectOutputStream;

public class MessageCreator {
    private MessageCreator() {
    }

    public static Message createMessage(Command command, String text, ObjectOutputStream out) {
        if (command == Command.SEND) {
            return new TextMessage(text);
        } else if (command == Command.HISTORY) {
            return new HistoryMessage(text, out);
        } else if (command == Command.REGISTER){
            return new LoginMessage(text, out);
        } else if (command == Command.CHANGE_ROOM){
            return new ChangeRoomMessage(text, out);
        }else {
            return null;
        }
    }
}
