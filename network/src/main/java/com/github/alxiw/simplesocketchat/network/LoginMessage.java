package com.github.alxiw.simplesocketchat.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class LoginMessage extends Message {
    private static final String USER_NAME_ALREADY_IN_USE_MESSAGE = "This username is already in use";

    private final Command command = Command.REGISTER;


    public LoginMessage(String userName, ObjectOutputStream out) {
        super(userName);
        this.out = out;
        if(userName != null){
            this.text = userName.trim();
        }
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void process(ObjectOutputStream out) {
        if (ServerState.userExist(text)) {
            System.out.println(USER_NAME_ALREADY_IN_USE_MESSAGE);
            try {
                out.writeObject(new ServerMessage(USER_NAME_ALREADY_IN_USE_MESSAGE));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        ServerState.addNewUser(text);
        User prevUser = ServerState.getUserStreamMap().get(out);

        if(out != null){
            handleNameChange(prevUser.getName(), out);
        }

        ServerState.getUserStreamMap().replace(out, new User(text, prevUser.getRoom()));
    }

    private void handleNameChange(String prevUserName, ObjectOutputStream out) {
        ServerState.getLoginSet().remove(prevUserName);
        User user = ServerState.getUserStreamMap().get(out);
        try {
            for (Map.Entry<ObjectOutputStream, User> entry : ServerState.getUserStreamMap().entrySet()) {
                if(user.getRoom().equals(entry.getValue().getRoom())){
                    entry.getKey().writeObject(new ServerMessage(
                            "user " + prevUserName + " changed name to " + text));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
