package com.edu.acme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class ServerApp {
    private static LinkedList<ObjectOutputStream> clientOutList = new LinkedList<>();
    private static File messageHistoryPath = new File("history.ser");

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9999)
        ) {
            while (true) {
                Socket client = server.accept();
                new Thread(() -> readFromClient(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFromClient(Socket client) {
        try (
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())
        ) {
            clientOutList.add(out);
            Message m;
            while (true) {
                System.out.println("New line from user");
                m = (Message)in.readObject();
                System.out.println(m.getText());
                m.setCurrentTime();
                sendMessageToAllConnectedClients(m);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SocketException t) {
            System.out.println("Connection has been lost");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("New connection");
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
        } catch (SocketException e){
            clientOutList.remove(out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveMessageToHistory(Message message) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(messageHistoryPath))) {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}