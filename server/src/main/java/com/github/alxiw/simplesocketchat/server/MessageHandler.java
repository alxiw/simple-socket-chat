package com.github.alxiw.simplesocketchat.server;

import com.github.alxiw.simplesocketchat.core.*;

import java.util.LinkedList;
import java.util.Map;

public class MessageHandler {

    private MessageHandler() {
        //private constructor
    }

    public static void handle(Message message, Connection connection) {
        if (message.getCommand() == Command.SEND) {
            sendMessage(message, connection);
        } else if (message.getCommand() == Command.HIST) {
            getHistory(message, connection);
        } else if (message.getCommand() == Command.NAME) {
            changeName(message, connection);
        } else if (message.getCommand() == Command.ROOM) {
            changeRoom(message, connection);
        }
    }

    private static void sendMessage(Message message, Connection connection) {
        message.setText(ServerState.getMap().get(connection).getName() + ": " + message.getText());
        sendMessageToAllRoomUsers(message, connection);
        saveToHistory(message, connection);
    }

    private static void getHistory(Message message, Connection connection) {
        User user = ServerState.getMap().get(connection);
        int room = user.getRoom();
        LinkedList<Message> messages = History.readListOfMessages(room);
        if (messages.isEmpty()) {
            connection.sendMessage(new Message(null, "HISTORY IS EMPTY"));
        } else {
            for (Message msg : messages) {
                connection.sendMessage(msg);
            }
        }
    }

    private static void changeName(Message message, Connection connection) {
        for (Map.Entry<Connection, User> entry : ServerState.getMap().entrySet()) {
            if (entry.getValue() != null && entry.getValue().getName() != null && entry.getValue().getName().equals(message.getText())) {
                connection.sendMessage(new Message(null, "ALREADY IN USE"));
                return;
            }
        }
        User user = ServerState.getMap().get(connection);
        if (connection != null) {
            sendMessagesAboutNameChange(user.getName(), message.getText(), connection);
        }
        ServerState.getMap().replace(connection, new User(message.getText(), user.getRoom()));
    }

    private static void changeRoom(Message message, Connection connection) {
        User user = ServerState.getMap().get(connection);
        if (validateRoom(message.getText())) {
            if (message.getText().equals(user.getRoom())) {
                connection.sendMessage(new Message(null, "YOU ARE ALREADY THERE"));
                return;
            }
            sendMessagesAboutRoomChange(connection, user, message.getText());
        } else {
            connection.sendMessage(new Message(null, "INCORRECT ROOM"));
        }
    }

    private static boolean validateRoom(String text) {
        int a;
        try {
            a = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return false;
        }
        if (a >= 0 && a < 10) {
            return true;
        } else {
            return false;
        }
    }


    private static void sendMessageToAllRoomUsers(Message message, Connection connection) {
        User user = ServerState.getMap().get(connection);
        for (Map.Entry<Connection, User> entry : ServerState.getMap().entrySet()){
            if (entry.getValue().getRoom() == user.getRoom()) {
                entry.getKey().sendMessage(message);
            }
        }
    }

    private static void saveToHistory(Message message, Connection connection) {
        User user = ServerState.getMap().get(connection);
        int room = user.getRoom();
        History.saveMessage(message, room);
    }

    private static void sendMessagesAboutNameChange(String prevUserName, String newUserName, Connection connection) {
        User user = ServerState.getMap().get(connection);
        for (Map.Entry<Connection, User> entry : ServerState.getMap().entrySet()) {
            if (user.getRoom() == entry.getValue().getRoom()){
                entry.getKey().sendMessage(new Message(null, prevUserName + " CHANGED NAME TO " + newUserName));
            }
        }
    }

    private static void sendMessagesAboutRoomChange(Connection connection, User user, String text) {
        User newUser = new User(user.getName(), Integer.parseInt(text));
        ServerState.getMap().put(connection, newUser);
        for (Map.Entry<Connection, User> entry : ServerState.getMap().entrySet()) {
            sendMessageToOldRoomUsers(user, newUser, entry);
            sendMessageToNewRoomUsers(user, newUser, entry);
        }
    }

    private static void sendMessageToOldRoomUsers(User user, User newUser, Map.Entry<Connection, User> entry) {
        if (entry.getValue().getRoom() == user.getRoom()) {
            entry.getKey().sendMessage(new Message(null, user.getName() + " CHANGED ROOM TO " + newUser.getRoom()));
        }
    }

    private static void sendMessageToNewRoomUsers(User user, User newUser, Map.Entry<Connection, User> entry) {
        if (entry.getValue().getRoom() == newUser.getRoom()) {
            entry.getKey().sendMessage(new Message(null, user.getName() + " JOINED THE ROOM"));
        }
    }
}
