package com.edu.acme.message;

import com.edu.acme.Command;
import com.edu.acme.ServerApp;
import com.edu.acme.ServerState;

import java.io.*;
import java.net.SocketException;
import java.util.List;

public class SendMessage extends Message {
    private final Command command = Command.SEND;
    private static File messageHistoryPath = new File("history.ser");

    public SendMessage(String text) {
        super(text);
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void process() {
        this.setCurrentTime();
        sendMessageToAll(ServerState.getClientOutList());
        saveToHistory();
    }

    private void saveToHistory() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(messageHistoryPath, true))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAll(List<ObjectOutputStream> clientOutList) {
        System.out.println("Have " + clientOutList.size() + " clients");
        for (ObjectOutputStream out : clientOutList) {
            try {
                out.writeObject(this);
                out.flush();
            } catch (SocketException e) {
                clientOutList.remove(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
