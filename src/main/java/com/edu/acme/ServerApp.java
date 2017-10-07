package com.edu.acme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class ServerApp {
    private static LinkedList<ObjectOutputStream> clientOutList = new LinkedList<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9999)
        ) {
//            new Thread(() -> sendToClientList()).start();
            while (true) {
                Socket client = server.accept();
                new Thread(() -> readFromClient(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFromClient(Socket client) {
        System.out.println("New connection");
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())
        ) {
            clientOutList.add(out);
            String inputMessage;
            while (true) {
                inputMessage = in.readLine();
                System.out.println(inputMessage);
                System.out.println("New line from user");
                sendMessageToAllConnectedClients(new Message(inputMessage));
            }
        } catch (SocketException t) {
            System.out.println("Потеряно соединение");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessageToAllConnectedClients(Message message) {
        System.out.println("Have " + clientOutList.size() + " clients");
        for (ObjectOutputStream out : clientOutList) {
            sendMessageToClient(message, out);
        }
    }

    private static void sendMessageToClient(Message message, ObjectOutputStream out) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}