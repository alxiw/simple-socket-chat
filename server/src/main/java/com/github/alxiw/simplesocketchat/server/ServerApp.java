package com.github.alxiw.simplesocketchat.server;

import com.github.alxiw.simplesocketchat.core.*;

import java.io.*;
import java.net.ServerSocket;

public class ServerApp implements Listener, Runnable {

    private final int port;

    private ServerApp(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ServerApp(9999).run();
    }

    @Override
    public void onConnectionReady(Connection connection) {
        System.out.println("ATTEMPTING TO CONNECT NEW CLIENT");
        ServerState.addClientConnection(connection);
        System.out.println("CLIENT CONNECTED WITH " + connection.toString());
    }

    @Override
    public void onMessageReceived(Connection connection, Message message) {
        MessageHandler.handle(message, connection);
        System.out.println("NEW " + message.getCommand().toString() + " MESSAGE RECEIVED\n" + message.toString());
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("CONNECTION " + connection.toString() + " IS OVER");
        ServerState.getMap().remove(connection);
        System.out.println("THERE ARE " + ServerState.getMap().size() + " CONNECTIONS");
    }

    @Override
    public void onException(Connection connection, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void run() {
        System.out.println("CHAT SERVER HAS BEEN LAUNCHED");
        try (ServerSocket server = new ServerSocket(port)) {
            boolean check = true;

            while (check) {
                try {
                    new Connection(this, server.accept());
                } catch (IOException e) {
                    System.out.println("CONNECTION ERROR\n" + e);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("CHAT SERVER HAS BEEN SHUT DOWN");
    }

}