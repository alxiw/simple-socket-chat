package com.edu.acme.message;

import com.edu.acme.Command;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, представляющий собой сообщение, посылаемое серверу.
 */
public abstract class Message implements Serializable {
    private String text;
    private String time;

    public Message(String text) {
        this.text = text;
    }

    public abstract Command getCommand();

    private String getTime() {
        return time;
    }

    public void setCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
    }

    public abstract void process(ObjectOutputStream out);

    @Override
    public String toString() {
        return "[" + getTime() + "]: " + text;
    }
}

