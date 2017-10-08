package com.edu.acme;

import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ServerState {
    private ServerState() {
    }

    private volatile static List<ObjectOutputStream> clientOutList = new LinkedList<>();

    public static List<ObjectOutputStream> getClientOutList() {
        return clientOutList;
    }

    public static void removeClientOut(ObjectOutputStream out) {
        clientOutList.remove(out);
    }

    public static void addClientOut(ObjectOutputStream out) {
        clientOutList.add(out);
    }
}
