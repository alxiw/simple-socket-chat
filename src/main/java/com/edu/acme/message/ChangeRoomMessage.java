package com.edu.acme.message;

import com.edu.acme.Command;
import com.edu.acme.ServerState;
import com.edu.acme.UserInfo;

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
        UserInfo userInfo = ServerState.getUserStreamMap().get(out);
        if(text.equals(userInfo.getRoom())){
            try {
                out.writeObject(new ServerMessage("You are already in this room"));
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        handeRoomChange(out, userInfo);
    }

    private void handeRoomChange(ObjectOutputStream out, UserInfo userInfo) {
        UserInfo newUserInfo = new UserInfo(userInfo.getUsername(), text);
        ServerState.getUserStreamMap().put(out, newUserInfo);
        try{
            for (Map.Entry<ObjectOutputStream, UserInfo> entry : ServerState.getUserStreamMap().entrySet()){
                sendMessageToOldRoomUsers(userInfo, newUserInfo, entry);
                sendMessageToNewRoomUsers(userInfo, newUserInfo, entry);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendMessageToNewRoomUsers(UserInfo userInfo, UserInfo newUserInfo, Map.Entry<ObjectOutputStream,
            UserInfo> entry) throws IOException {
        if (entry.getValue().getRoom().equals(newUserInfo.getRoom())){
            entry.getKey().writeObject(new ServerMessage(
                    "user " + userInfo.getUsername() + " joined the room "));
        }
    }

    private void sendMessageToOldRoomUsers(UserInfo userInfo, UserInfo newUserInfo, Map.Entry<ObjectOutputStream,
            UserInfo> entry) throws IOException {
        if (entry.getValue().getRoom().equals(userInfo.getRoom())){
            entry.getKey().writeObject(new ServerMessage(
                    "user " + userInfo.getUsername() + " changed room to " + newUserInfo.getRoom()));
        }
    }
}
