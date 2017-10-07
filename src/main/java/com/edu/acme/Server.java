package com.edu.acme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("InfiniteLoopStatement")
public class Server {
    private static LinkedList<Socket> clientList = new LinkedList<>();
    private static Queue<Message> messageQueue = new LinkedList<>();
    private static final int PORT = 9999;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            new Thread(Server::sendToClientList).start();
            while (true) {
                Socket client = server.accept();
                clientList.add(client);
                new Thread(() -> clientLoop(client)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clientLoop(Socket client) {
        try {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            while(true){
                Message message = (Message) in.readObject();
                messageQueue.add(message);
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void sendToClientList() {
        while (true) {
            if (messageQueue.size() > 0) {
                for (int i = 0; i < clientList.size(); i++) {
                    try (ObjectOutputStream out = new ObjectOutputStream(clientList.get(i).getOutputStream())) {
                        out.writeObject(messageQueue.peek());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                messageQueue.poll();
            }
        }
    }
}
