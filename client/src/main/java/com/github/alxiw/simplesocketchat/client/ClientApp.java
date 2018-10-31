package com.github.alxiw.simplesocketchat.client;

import com.github.alxiw.simplesocketchat.core.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ClientApp implements Listener, Runnable {

    private final String host;
    private final int port;
    private Connection connection;
    private static MessageValidator messageValidator = new MessageValidator();

    private ClientApp(String host, int port) {
        this.host = host;
        this.port = port;
        if (isValidHost(this.host) && isValidPort(this.port)) {
            try {
                connection = new Connection(this, this.host, this.port);
            } catch (IOException e) {
                System.out.println("CONNECTION ERROR\n" + e);
            }
        } else {
            System.out.println("INCORRECT ADDRESS");
        }
    }

    public static void main(String[] args) {
        new ClientApp("localhost", 9999).run();
    }

    @Override
    public void onConnectionReady(Connection connection) {
        System.out.println("CONNECTION IS READY");
    }

    @Override
    public void onMessageReceived(Connection connection, Message message) {
        System.out.println(message.toString());
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("CONNECTION " + connection.toString() + " IS OVER");
    }

    @Override
    public void onException(Connection connection, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean check = true;
        while (check){
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            createMessageAndSend(line);
        }
    }

    private void createMessageAndSend(String line) {
        String errorDescription = messageValidator.getErrorDescription(line);
        if (errorDescription == null) {
            String[] parts = line.split("\\s+", 2);
            String command;
            String text;
            if (parts.length > 1) {
                command = parts[0];
                text = parts[1];
            } else {
                command = parts[0];
                text = "";
            }
            Message message = MessageCreator.createMessage(Command.get(command.toLowerCase()), text);
            this.connection.sendMessage(message);
        } else {
            System.out.println(errorDescription);
        }
    }

    private static boolean isValidHost(String host) {
        if (host == null || host.isEmpty()) return false;
        host = host.trim();
        if ((host.length() < 6) || (host.length() > 15)) return false;
        if (host.equals("localhost")) return true;
        try {
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(host);
            return matcher.matches();
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    private static boolean isValidPort(int port) {
        return port > 0 && port <= 49151;
    }
}