package com.edu.acme;

import com.edu.acme.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

public class ServerApp {
    private static List<ObjectOutputStream> clientOutList = new LinkedList<>();
    private static final int PORT = 9999;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)
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
                m = (Message)in.readObject();
                m.process();
                System.out.println("New message " + m.getCommand() + " from user" + m.toString());
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

    public static List<ObjectOutputStream> getClientOutList() {
        return clientOutList;
    }
}