package com.github.alxiw.simplesocketchat.network;

import java.io.*;
import java.util.LinkedList;

public class HistoryMessage extends Message {
    private Command command = Command.HISTORY;

    public HistoryMessage(String text, ObjectOutputStream out) {
        super(text);
        this.out = out;
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void process(ObjectOutputStream out) {
        User user = ServerState.getUserStreamMap().get(out);
        String room = user.getRoom();
        LinkedList<TextMessage> messages = History.readMessages(room);
        if (messages.size() == 0) {
            try {
                out.writeObject(new ServerMessage("History is empty"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            for (TextMessage message : messages) {
                try {
                    out.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
