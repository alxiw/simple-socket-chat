package com.edu.acme;

import java.util.Objects;

/**
 * Класс для хранения различных команд
 */
public enum Command {
    SEND("/snd"),
    SEND_ALL("/hist"),
    REGISTER("/chid");

    private final String command;

    Command(String str) {
        command = str;
    }

    /**
     * Проверяет, является ли введенная строка командой
     *
     * @param pType введенная строка, которую нужно проверить
     * @throws IllegalArgumentException исключение
     */
    public static void checkCommand(String pType) throws IllegalArgumentException {
        for (Command command : Command.values()) {
            if (command.getCommand().equals(pType)) {
                return;
            }
        }
        throw new IllegalArgumentException("Bad command name");
    }

    public String getCommand() {
        return command;
    }

    public static Command get(String stringCommand) {
        for(Command s : values()) {
            if(Objects.equals(s.command, stringCommand)) return s;
        }
        return null;
    }

}
