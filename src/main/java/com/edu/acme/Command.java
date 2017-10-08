package com.edu.acme;

import java.util.Objects;

/**
 * Класс для хранения различных команд
 */
public enum Command {
    SEND("/snd"),
    HISTORY("/hist"),
    REGISTER("/chid");

    private final String command;

    Command(String str) {
        command = str;
    }
//
//    /**
//     * Проверяет, является ли введенная строка командой
//     *
//     * @param commandToCheck введенная строка, которую нужно проверить
//     */
//    public static boolean checkCommand(String commandToCheck) {
//        for (Command command : Command.values()) {
//            if (!command.getCommand().equals(commandToCheck)) {
//                return false;
//            }
//        }
//        return true;
//    }

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
