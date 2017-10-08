package com.edu.acme;

import com.edu.acme.message.Message;
import com.edu.acme.message.MessageFactory;
import com.edu.acme.message.MessageValidator;
import com.edu.acme.message.Validator;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientApp {
    private ClientApp() {
    }

    private static final int PORT = 9999;
    private static Validator messageValidator = new MessageValidator();
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", PORT);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            ObjectInputStream messagesReader = new ObjectInputStream(
                    new BufferedInputStream(
                            socket.getInputStream())
            )
        ) {
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
                out.writeObject(MessageFactory.createMessage(messageParts[0], messageParts[1], out));
            } else {
                out.writeObject(MessageFactory.createMessage(messageParts[0], null, out));
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
//            try {
//                Thread.sleep(10_000);
//                System.err.println("Try to reconnect");
//                readMessageLoop(messagesReader);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            System.exit(1);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}