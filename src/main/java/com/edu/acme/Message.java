package com.edu.acme;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
    private Command command;
    private String text;
    private String time;

    public Message(Command command, String text) {
        this.command = command;
        this.text = text;
    }

    public Command getCommand() {
        return command;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public void setCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
    }

    @Override
    public String toString() {
        return "[" +getTime() + "]: " + getText();
    }
}

