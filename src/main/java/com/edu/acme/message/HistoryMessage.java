package com.edu.acme.message;

import com.edu.acme.Command;
import com.edu.acme.History;
import com.edu.acme.ServerState;
import com.edu.acme.UserInfo;

import java.io.*;
import java.util.LinkedList;

import static com.edu.acme.ServerState.getMessageHistoryPath;

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
        UserInfo userInfo = ServerState.getUserStreamMap().get(out);
        String room = userInfo.getRoom();
        LinkedList<TextMessage> messages = History.readMessages(room, 5);
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
