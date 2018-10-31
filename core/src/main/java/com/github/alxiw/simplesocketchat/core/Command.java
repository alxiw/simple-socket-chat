package com.github.alxiw.simplesocketchat.core;

import java.util.Objects;

public enum Command {

    SEND("/send"),
    NAME("/name"),
    ROOM("/room"),
    HIST("/hist"),
    EXIT("/exit"),
    INFO("/info");

    private final String cmd;

    Command(String str) {
        cmd = str;
    }

    public static Command get(String string) {
        for (Command s : values()) if (Objects.equals(s.cmd, string)) return s;
        return null;
    }

}
