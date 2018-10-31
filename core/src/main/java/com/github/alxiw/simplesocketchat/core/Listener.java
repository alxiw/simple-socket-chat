package com.github.alxiw.simplesocketchat.core;

public interface Listener {
    void onConnectionReady(Connection connection);
    void onMessageReceived(Connection connection, Message message);
    void onDisconnect(Connection connection);
    void onException(Connection connection, Exception e);
}
