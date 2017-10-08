package com.edu.acme;

import java.time.format.DateTimeFormatter;

public class UserInfo {
    private String username;
    private String room;

    public UserInfo(String username) {
        this.username = username;
        this.room = ServerState.DEFAULT_ROOM;
    }

    public UserInfo(String username, String room) {
        this.username = username;
        this.room = room;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
