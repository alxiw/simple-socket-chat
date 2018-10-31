package com.github.alxiw.simplesocketchat.client;

import com.github.alxiw.simplesocketchat.core.*;

public class MessageCreator {

    public static final int DEFAULT_ROOM = 0;

    private MessageCreator() {
        //private constructor
    }

    public static Message createMessage(Command command, String text) {
        if (command == Command.SEND) {
            return new Message(Command.SEND, text);
        } else if (command == Command.HIST) {
            return new Message(Command.HIST, text);
        } else if (command == Command.NAME){
            if (text != null){
                text = text.trim();
            }
            return new Message(Command.NAME, text);
        } else if (command == Command.ROOM){
            if(text == null || text.trim().equals(""))
                text = String.valueOf(DEFAULT_ROOM);
            else {
                text = text.trim();
            }
            return new Message(Command.ROOM, text);
        } else {
            return new Message(null, text);
        }
    }

}
