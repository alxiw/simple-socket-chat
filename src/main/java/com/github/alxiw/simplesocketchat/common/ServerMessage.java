package com.github.alxiw.simplesocketchat.common;

import java.io.ObjectOutputStream;


public class ServerMessage extends Message {

    public ServerMessage(String text) {
        super(text);
    }

    @Override
    public Command getCommand() {
        return null;
    }


    @Override
    public void process(ObjectOutputStream out) {

    }

}