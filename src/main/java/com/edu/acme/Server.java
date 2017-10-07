package com.edu.acme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    private static LinkedList<Socket> clientList = new LinkedList<>();
    private static Queue<Message> messageQueue = new LinkedList<>();
    private static final int PORT = 9999;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)
        ) {
            new Thread(Server::sendToClientList);
            while (true) {
                Socket client = server.accept();
                clientList.add(client);
                new Thread(() -> clientProcess(client)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clientProcess(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String message;
            do {
                message = in.readLine();
                messageQueue.add(new Message(message));
                System.out.println(message);

            } while (message != null);

        } catch (IOException e) {
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
