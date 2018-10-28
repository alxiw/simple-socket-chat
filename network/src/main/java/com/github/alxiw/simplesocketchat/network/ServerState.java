package com.github.alxiw.simplesocketchat.network;

import java.io.ObjectOutputStream;
import java.util.*;


public class ServerState {

    private static volatile long anonUserCount = 0;

    private ServerState() {
        //private constructor
    }

    public static final String DEFAULT_ROOM = "default";

    public static Set<String> getLoginSet() {
        return loginSet;
    }

    private static volatile Set<String> loginSet = new HashSet<>();


    public static Map<ObjectOutputStream, User> getUserStreamMap() {
        return userStreamMap;
    }

    private static volatile Map<ObjectOutputStream, User> userStreamMap = new HashMap<>();


    public static void addClientOut(ObjectOutputStream out){
        User user = userStreamMap.get(out);
        if (user == null){
            user = new User(getAnonymousName());
        }
        userStreamMap.putIfAbsent(out, user);
    }

    private static String getAnonymousName() {
        return "anonymous#" + anonUserCount++;
    }

    public static boolean userExist(String name){
        return loginSet.contains(name);
    }

    public static void addNewUser(String username){
        loginSet.add(username);
    }

}
