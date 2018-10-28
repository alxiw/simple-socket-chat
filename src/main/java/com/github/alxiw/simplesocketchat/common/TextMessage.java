package com.github.alxiw.simplesocketchat.common;

import com.github.alxiw.simplesocketchat.server.History;
import com.github.alxiw.simplesocketchat.server.ServerState;
import com.github.alxiw.simplesocketchat.server.User;

import java.io.*;
import java.net.SocketException;
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
        this.text = ServerState.getUserStreamMap().get(out).getName() + ": " + text;
        sendMessageToAllRoomUsers(out);
        saveToHistory(out);
    }

    private void sendMessageToAllRoomUsers(ObjectOutputStream out) {
        User user = ServerState.getUserStreamMap().get(out);
        for (Map.Entry<ObjectOutputStream, User> entry : ServerState.getUserStreamMap().entrySet()){
            try {
                if (entry.getValue().getRoom().equals(user.getRoom())) {
                    entry.getKey().writeObject(this);
                }
            } catch (SocketException e) {
                ServerState.getUserStreamMap().remove(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToHistory(ObjectOutputStream out) {
        User user = ServerState.getUserStreamMap().get(out);
        String room = user.getRoom();
        History.saveMessage(this, room);
    }

}
