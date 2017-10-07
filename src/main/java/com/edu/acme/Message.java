package com.edu.acme;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
    private String message;
    private Date dateTime;

    public Message(String message) {
        this.message = message;
        this.dateTime = new Date();
    }

    @Override
    public String toString() {
        return message + dateTime.toString();
    }
}

