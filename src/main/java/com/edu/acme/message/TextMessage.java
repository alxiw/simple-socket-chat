package com.edu.acme.message;

import com.edu.acme.Command;
import com.edu.acme.History;
import com.edu.acme.ServerState;
import com.edu.acme.UserInfo;

import java.io.*;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

public class TextMessage extends Message {
    private final Command command = Command.SEND;

    public TextMessage(String text) {
        super(text);
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void process(ObjectOutputStream out) {
        this.setCurrentTime();
        this.setText(ServerState.getUserStreamMap().get(out).getUsername() + ": " + text);
        UserInfo userInfo = ServerState.getUserStreamMap().get(out);
        for (Map.Entry<ObjectOutputStream, UserInfo> entry : ServerState.getUserStreamMap().entrySet()){
            try {
                if (entry.getValue().getRoom().equals(userInfo.getRoom())) {
                    entry.getKey().writeObject(this);
                }
            } catch (SocketException e) {
                ServerState.getLoginSet().remove(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveToHistory(out);
    }

    private void saveToHistory(ObjectOutputStream out) {
        UserInfo userInfo = ServerState.getUserStreamMap().get(out);
        String room = userInfo.getRoom();
        History.saveMessage(this, room);
    }

}
