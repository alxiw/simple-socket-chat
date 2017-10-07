package com.edu.acme;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                Socket client = new Socket("localhost", 9999);
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());
                Scanner keyboardReader = new Scanner(System.in)
        ) {
            new Thread(() -> {
                try {
                    while (true) {
                        while (true) {
                            System.out.println(in.readUTF());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            while (true) {
                out.writeUTF(keyboardReader.nextLine());
            }
        } catch (ConnectException e) {
            System.out.println("Can't connect, server is down");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public static void startThreadIn(Socket client) throws IOException, ClassNotFoundException {
//        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream((client.getInputStream())))) {
//            while (true) {
//                System.out.println("qwe");
//                System.out.println(((Message)in.readObject()).toString());
//            }
//        } catch (SocketException e) {
//            System.out.println("Lost connection");
//            System.exit(111);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void startThreadOut(PrintWriter out, Scanner in) {

    }
}