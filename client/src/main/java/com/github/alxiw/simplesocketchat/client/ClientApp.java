package com.github.alxiw.simplesocketchat.client;

import com.github.alxiw.simplesocketchat.network.Command;
import com.github.alxiw.simplesocketchat.network.MessageCreator;
import com.github.alxiw.simplesocketchat.network.MessageValidator;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientApp {

    private static final int PORT = 9999;
    private static MessageValidator messageValidator = new MessageValidator();

    private ClientApp() {
        //private constructor
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream messagesReader = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {
            new Thread(() -> readMessageLoop(messagesReader)).start();
            while (true) {
                String message = consoleReader.readLine();
                createMessageAndSend(out, message);
            }
        } catch (IOException e) {
            System.err.println("Lost connection");
        }
    }

    private static void createMessageAndSend(ObjectOutputStream out, String message) throws IOException {
        String errorMessage = messageValidator.getErrorDescription(message);
        if (errorMessage == null) {
            String[] messageParts = message.split("\\s+", 2);
            if(messageParts.length > 1){
                out.writeObject(MessageCreator.createMessage(Command.get(messageParts[0]), messageParts[1], out));
            } else {
                out.writeObject(MessageCreator.createMessage(Command.get(messageParts[0]), null, out));
            }
            out.flush();
        } else {
            System.out.println(errorMessage);
        }
    }

    private static void readMessageLoop(ObjectInputStream messagesReader) {
        try {
            while (true){
                System.out.println(messagesReader.readObject().toString());
            }
        } catch (SocketException e) {
            System.err.println("Lost connection");
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}