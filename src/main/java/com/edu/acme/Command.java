package com.edu.acme;

import java.util.Objects;

/**
 * Класс для хранения различных команд
 */
public enum Command {
    SEND("/snd"),
    HISTORY("/hist"),
    REGISTER("/chid"),
    CHANGE_ROOM("/chroom");

    private final String command;

    Command(String str) {
        command = str;
    }

    /**
     * Геттер для команд
     * @param stringCommand - команда
     */
    public static Command get(String stringCommand) {
        for(Command s : values()) {
            if(Objects.equals(s.command, stringCommand)) return s;
        }
        return null;
    }

}
