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
            out.println(gson.toJson(message));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<TextMessage> readMessages(String filename, int number) {
        Gson gson = new Gson();
        LinkedList<TextMessage> messages = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getPath(filename).toString()))) {
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                TextMessage message = gson.fromJson(line, TextMessage.class);
                messages.add(message);
                count++;
                if (count > number) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private static Path getPath(String filename) {
        System.out.println(Paths.get("history", filename));
        return Paths.get("history", filename + ".ser");
    }
}
