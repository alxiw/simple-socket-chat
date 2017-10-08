package com.edu.acme;

import com.edu.acme.message.MessageCreator;
import com.edu.acme.message.MessageValidator;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Класс, реализующий клиентскую часть приложения
 */
public class ClientApp {
    /**
     * Пустой приватный класс для предотвращения создания экземпляра класса
     */
    private ClientApp() {
    }

    /**
     * Поле, хранящее номер порта сокета
     */
    private static final int PORT = 9999;

    /**
     * Создание валидатора сообщений
     */
    private static MessageValidator messageValidator = new MessageValidator();

    /**
     * Главный метод, запускающий клиентскую часть приложения
     */
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

    /**
     * Метод, позволяющий серверу создавать сообщения и отправлять их клиентам
     */
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

    /**
     * Метод, позволяющий принимать сообщения сервера
     */
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