package com.github.alxiw.simplesocketchat.server;

import com.github.alxiw.simplesocketchat.server.ServerState;

public class User {

    private String name;
    private String room;

    public User(String name) {
        this.name = name;
        this.room = ServerState.DEFAULT_ROOM;
    }

    public User(String name, String room) {
        this.name = name;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
