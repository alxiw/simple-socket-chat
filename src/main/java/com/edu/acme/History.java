package com.edu.acme;

import com.edu.acme.message.Message;
import com.edu.acme.message.TextMessage;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class History {
    public static void saveMessage(Message message, String filename) {
        Gson gson = new Gson();
        try (PrintWriter out = new PrintWriter(new FileOutputStream(getPath(filename).toString(), true))) {
            System.out.println("write");
            out.println(gson.toJson(message));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<TextMessage> readMessages(String filename) {
        Gson gson = new Gson();
        LinkedList<TextMessage> messages = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getPath(filename).toString()))) {
            String line;
            while ((line = br.readLine()) != null) {
                TextMessage message = gson.fromJson(line, TextMessage.class);
                messages.add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private static Path getPath(String filename) {
        return Paths.get(filename + ".ser");
    }
}
