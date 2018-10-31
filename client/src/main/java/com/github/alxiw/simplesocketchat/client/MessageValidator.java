package com.github.alxiw.simplesocketchat.client;

import com.github.alxiw.simplesocketchat.core.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageValidator {

    private static final int MAX_MESSAGE_LENGTH = 150;
    private static final String HELP_MESSAGE =
    " /" + Command.SEND.toString() + " : Send a message\n" +
    " /" + Command.HIST.toString() + " : Watch history\n" +
    " /" + Command.NAME.toString() + " : Change name\n" +
    " /" + Command.ROOM.toString() + " : Change room";

    public String getErrorDescription(String message) {
        String[] array = message.split("\\s+", 2);
        if (!isCommand(array[0])) {
            return HELP_MESSAGE;
        } else if ((array.length < 2 || array[1].equals("") || array[1].equals(" ")) && Command.get(message) == Command.SEND) {
            return "EMPTY MESSAGE";
        } else if (array.length == 2 && array[1].length() >= MAX_MESSAGE_LENGTH) {
            return "TOO LONG MESSAGE";
        } else {
            return null;
        }
    }

    private boolean isCommand(String value) {
        Pattern p = Pattern.compile("^\\/[a-zA-Z]{4}( )?( .*)?$");
        if (value != null) {
            Matcher m = p.matcher(value);
            return m.matches();
        } else {
            return false;
        }
    }

}