package com.edu.acme.message;

import com.edu.acme.Command;
import com.edu.acme.ServerState;

import java.io.*;

import static com.edu.acme.ServerState.getMessageHistoryPath;

public class HistoryMessage extends Message {
    private Command command = Command.HISTORY;

    public HistoryMessage(String text, ObjectOutputStream out) {
        super(text);
        this.out = out;
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void process(ObjectOutputStream out) {
//        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(getMessageHistoryPath()))) {
//            System.out.println("Try to read messages from file");
//            Message message = (Message) in.readObject();
//            if (message == null || !ServerState.getMessageHistoryPath().exists()) {
//                sendEmptyBack(out);
//            }
//            while (message != null) {
//                System.out.println(message);
//                out.writeObject(message);
//                message = (Message) in.readObject();
//            }
//        } catch (FileNotFoundException e) {
//            try {
//                sendEmptyBack(out);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//        catch (InvalidClassException e) {
//            System.out.println("Can't read. Delete old messages");
//            ServerState.getMessageHistoryPath().delete();
//        }
//        catch (ClassNotFoundException | IOException e) {
//            e.printStackTrace();
//        }
        if (ServerState.messageHistory.size() == 0) {
            try {
                sendEmptyBack(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            for (Message message : ServerState.messageHistory) {
                try {
                    out.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendEmptyBack(ObjectOutputStream out) throws IOException {
        Message answer = MessageFactory.createMessage(Command.SEND, "History is empty", null);
        out.writeObject(answer);
    }
}
