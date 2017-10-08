package com.edu.acme;

import com.edu.acme.message.Message;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ServerState {
    private volatile static List<ObjectOutputStream> clientOutList = new LinkedList<>();
    private static File messageHistoryPath = new File("history.ser");
    public volatile static LinkedList<Message> messageHistory = new LinkedList<>();

    public static List<ObjectOutputStream> getClientOutList() {
        return clientOutList;
    }

    public static void removeClientOut(ObjectOutputStream out) {
        clientOutList.remove(out);
    }

    public static void addClientOut(ObjectOutputStream out) {
        clientOutList.add(out);
    }

    public static File getMessageHistoryPath() {
        return messageHistoryPath;
    }
}
