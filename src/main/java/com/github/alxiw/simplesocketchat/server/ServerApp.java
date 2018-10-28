package com.github.alxiw.simplesocketchat.server;

import com.github.alxiw.simplesocketchat.common.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class ServerApp {

    private ServerApp() {
        //private constructor
    }

    private static final int PORT = 9999;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket client = server.accept();
                System.out.println("New connection");
                new Thread(() -> readFromClient(client)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readFromClient(Socket client) {
        try (ObjectInputStream in = new ObjectInputStream(client.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
            ServerState.addClientOut(out);
            Message message;
            while (true) {
                message = (Message) in.readObject();
                message.process(out);
                System.out.println("New message " + message.getCommand() + " from user" + message.toString() + " proceeded");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SocketException t) {
            System.out.println("Connection has been lost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}