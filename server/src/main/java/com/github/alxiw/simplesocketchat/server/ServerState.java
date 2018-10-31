package com.github.alxiw.simplesocketchat.server;

import com.github.alxiw.simplesocketchat.core.Connection;

import java.util.*;

public class ServerState {

    private static final Map<Connection, User> map = Collections.synchronizedMap(new HashMap<>());

    private ServerState() {
        //private constructor
    }

    public static Map<Connection, User> getMap() {
        return map;
    }

    public static void addClientConnection(Connection connection){
        User user = map.get(connection);
        if (user == null) {
            user = new User(getDefaultName(connection));
        }
        map.putIfAbsent(connection, user);
    }

    private static String getDefaultName(Connection connection) {
        return "USER-" + connection.toString().replace(':', '-');
    }

}
