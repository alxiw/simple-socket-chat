package com.edu.acme;

public enum Command {
    SEND("/snd"),
    SEND_ALL("/hist"),
    REGISTER("/chid");

    private final String command;

    Command(String str) {
        command = str;
    }

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

}
