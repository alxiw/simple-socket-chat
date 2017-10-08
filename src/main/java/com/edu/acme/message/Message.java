package com.edu.acme.message;

import com.edu.acme.Command;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, представляющий собой сообщение, посылаемое серверу.
 */
public abstract class Message implements Serializable {
    protected String text;
    protected String time;
    protected transient ObjectOutputStream out;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public ObjectOutputStream getOutputStream() {
        return out;
    }

    public void setOutputStream(ObjectOutputStream out) {
        this.out = out;
    }




    public Message(String text) {
        this.text = text;
        this.time = new Date().toString();
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

    public abstract void process();


    @Override
    public String toString() {
        return "[" + getTime() + "]: " + text;
    }
}

