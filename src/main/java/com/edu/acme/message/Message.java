package com.edu.acme.message;

import com.edu.acme.Command;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Message implements Serializable{
    private String text;
    private String time;

    public Message(String text) {
        this.text = text;
    }

    public abstract Command getCommand();

    public String getText() {
        return text;
    }

    private String getTime() {
        return time;
    }

    public void setCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
    }

    public abstract void process();

    @Override
    public String toString() {
        return "[" +getTime() + "]: " + getText();
    }
}

