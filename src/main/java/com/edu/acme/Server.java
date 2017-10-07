package com.edu.acme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Server {
    private static LinkedList<DataOutputStream> clientOutList = new LinkedList<>();

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
                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream())
        ) {
            clientOutList.add(out);
            String inputMessage;
            while (true) {
                inputMessage = in.readUTF();
                System.out.println(inputMessage);
                System.out.println("New line from user");
                sendMessageToAllConnectedClients("New message: " + inputMessage);
            }
        } catch (SocketException t) {
            System.out.println("Потеряно соединение");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessageToAllConnectedClients(String message) {
        System.out.println("Have " + clientOutList.size() + " clients");
        for (DataOutputStream out : clientOutList) {
            sendMessageToClient(message, out);
        }
    }

    private static void sendMessageToClient(String message, DataOutputStream out) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
