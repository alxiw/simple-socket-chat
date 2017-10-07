package com.edu.acme.message;

import com.edu.acme.Command;

public class SendMessage extends Message {
    private final Command command = Command.SEND;

    public SendMessage(String text) {
        super(text);
    }

    @Override
    public Command getCommand() {
        return null;
    }

    @Override
    public void process() {
        // Сохранить себя в очередь, на диск
    }
}
