package com.github.alxiw.simplesocketchat.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ChangeRoomMessage extends Message{
    public ChangeRoomMessage(String text, ObjectOutputStream out) {
        super(text);
        if(text == null || text.trim().equals(""))
            this.text = ServerState.DEFAULT_ROOM;
        else {
            this.text = text.trim();

        }
        this.out = out;
    }

    @Override
    public Command getCommand() {
        return null;
    }

    @Override
    public void process(ObjectOutputStream out) {
        User user = ServerState.getUserStreamMap().get(out);
        if(text.equals(user.getRoom())){
            try {
                out.writeObject(new ServerMessage("You are already in this room"));
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        handeRoomChange(out, user);
    }

    private void handeRoomChange(ObjectOutputStream out, User user) {
        User newUser = new User(user.getName(), text);
        ServerState.getUserStreamMap().put(out, newUser);
        try{
            for (Map.Entry<ObjectOutputStream, User> entry : ServerState.getUserStreamMap().entrySet()){
                sendMessageToOldRoomUsers(user, newUser, entry);
                sendMessageToNewRoomUsers(user, newUser, entry);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendMessageToNewRoomUsers(User user, User newUser, Map.Entry<ObjectOutputStream,
            User> entry) throws IOException {
        if (entry.getValue().getRoom().equals(newUser.getRoom())){
            entry.getKey().writeObject(new ServerMessage(
                    "user " + user.getName() + " joined the room "));
        }
    }

    private void sendMessageToOldRoomUsers(User user, User newUser, Map.Entry<ObjectOutputStream,
            User> entry) throws IOException {
        if (entry.getValue().getRoom().equals(user.getRoom())){
            entry.getKey().writeObject(new ServerMessage(
                    "user " + user.getName() + " changed room to " + newUser.getRoom()));
        }
    }
}
