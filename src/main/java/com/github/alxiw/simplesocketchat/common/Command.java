package com.github.alxiw.simplesocketchat.common;

import java.util.Objects;

public enum Command {
    SEND("/snd"),
    HISTORY("/hist"),
    REGISTER("/chid"),
    CHANGE_ROOM("/chroom");

    private final String cmd;

    Command(String str) {
        cmd = str;
    }

    public static Command get(String stringCommand) {
        for(Command s : values()) {
            if(Objects.equals(s.cmd, stringCommand)) return s;
        }
        return null;
    }

}
