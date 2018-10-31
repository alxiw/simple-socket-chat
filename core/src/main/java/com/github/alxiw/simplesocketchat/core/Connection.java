package com.github.alxiw.simplesocketchat.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {

    private final Listener listener;
    private final Socket socket;

    private BufferedReader in;
    private BufferedWriter out;
    private Thread thread;

    private Gson gson = new GsonBuilder().create();

    public Connection(Listener listener, String ip, int port) throws IOException {
        this(listener, new Socket(ip, port));
    }

    public Connection(Listener listener, Socket socket) throws IOException {
        this.listener = listener;
        this.socket = socket;
        initialize();
    }

    private void initialize() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        thread = new Thread(() -> {
            try {
                Message message;
                listener.onConnectionReady(Connection.this);
                while (!thread.isInterrupted()) {
                    if (in.ready()) {
                        String line = in.readLine();
                        try {
                            message = gson.fromJson(line, Message.class);
                        } catch (JsonSyntaxException e) {
                            message = null;
                        }
                        listener.onMessageReceived(Connection.this, message);
                    }
                }
            } catch (IOException e) {
                listener.onException(Connection.this, e);
            } finally {
                listener.onDisconnect(Connection.this);
            }
        });
        thread.start();
    }

    public synchronized void sendMessage(Message message) {
        try {
            out.write(gson.toJson(message));
            out.newLine();
            out.flush();
        } catch (IOException e) {
            listener.onException(Connection.this, e);
            disconnect();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void disconnect() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            listener.onException(Connection.this, e);
        }
    }

    @Override
    public String toString() {
        return socket.getInetAddress().toString().replace("/", "") + ":" + socket.getPort();
    }

}
