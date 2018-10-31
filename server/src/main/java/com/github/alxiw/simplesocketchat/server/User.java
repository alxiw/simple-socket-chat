package com.github.alxiw.simplesocketchat.server;

public class User {

    public static final int DEFAULT_ROOM = 0;

    private String name;
    private int room;

    public User(String name) {
        this.name = name;
        this.room = DEFAULT_ROOM;
    }

    public User(String name, int room) {
        this.name = name;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

}
