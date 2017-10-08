package com.edu.acme;

import com.edu.acme.message.Message;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.*;

public class ServerState {
    private volatile static long anonUserCount = 0;
    private ServerState() {
    }

    public static final String DEFAULT_ROOM = "default";

    private volatile static List<ObjectOutputStream> clientOutList = new LinkedList<>();
    private static File messageHistoryPath = new File("history.ser");
    public volatile static LinkedList<Message> messageHistory = new LinkedList<>();

    public static Set<String> getLoginSet() {
        return loginSet;
    }

    private volatile static Set<String> loginSet = new HashSet<>();

    public static Map<ObjectOutputStream, UserInfo> getUserStreamMap() {
        return userStreamMap;
    }

    private volatile static Map<ObjectOutputStream, UserInfo> userStreamMap = new HashMap<>();

    public static List<ObjectOutputStream> getClientOutList() {
        //return clientOutList;
        return new LinkedList<>(userStreamMap.keySet());
    }

    public static void removeClientOut(ObjectOutputStream out) {
        clientOutList.remove(out);
    }

   /* public static void addClientOut(ObjectOutputStream out) {
        clientOutList.add(out);
    }*/

    public static void addClientOut(ObjectOutputStream out){
        UserInfo userInfo = userStreamMap.get(out);
        if (userInfo == null){
            userInfo = new UserInfo(getAnonymousName());
        }
        userStreamMap.putIfAbsent(out, userInfo);
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

    public static File getMessageHistoryPath() {
        return messageHistoryPath;
    }
}
