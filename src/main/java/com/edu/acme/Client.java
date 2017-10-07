package com.edu.acme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                Socket client = new Socket("localhost", 9900);
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                Scanner in = new Scanner(System.in)
        ) {
            Thread threadIn = new Thread(() -> {
                try {
                    startThreadIn(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            threadIn.start();
            Thread threadOut = new Thread(() -> startThreadOut(out, in));
            threadOut.start();
        } catch (ConnectException e) {
            System.out.println("Can't connect, server is down");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void startThreadIn(Socket client) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        try {
            while (true) System.out.println(reader.readLine());
        } catch (SocketException e) {
            System.out.println("Lost connection");
            System.exit(111);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startThreadOut(PrintWriter out, Scanner in) {
        while (true) {
            out.println(in.nextLine());
        }
    }
}