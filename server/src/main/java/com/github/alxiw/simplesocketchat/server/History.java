package com.github.alxiw.simplesocketchat.server;

import com.github.alxiw.simplesocketchat.core.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.LinkedList;

public class History {

    private static final String PREFIX = "room";
    private static final String LAYOUT = ".txt";
    private static final Gson GSON = new GsonBuilder().create();

    public static void saveMessage(Message message, int room) {
        File file = new File(PREFIX + room + LAYOUT);
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            writer.println(GSON.toJson(message));
            System.out.println("ADDED TO HISTORY");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static LinkedList<Message> readListOfMessages(int room) {
        File file = new File(PREFIX + room + LAYOUT);
        LinkedList<Message> messages = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Message message = GSON.fromJson(line, Message.class);
                messages.add(message);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
