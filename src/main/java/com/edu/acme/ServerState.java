package com.edu.acme;

import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Класс для хранения информации о состояния сервера
 */
public class ServerState {
    private volatile static long anonUserCount = 0;
    /**
     * Пустой приватный класс для предотвращения создания экземпляра класса
     */
    private ServerState() {
    }

    public static final String DEFAULT_ROOM = "default";

    public static Set<String> getLoginSet() {
        return loginSet;
    }

    /**
     * Список хранения никнеймов пользователей
     */
    private volatile static Set<String> loginSet = new HashSet<>();

    /**
     * Геттер списка хранения пользовательской информации и потоков им соответствующих
     */
    public static Map<ObjectOutputStream, UserInfo> getUserStreamMap() {
        return userStreamMap;
    }

    /**
     * Карта соответствия информации клиентов и потоков им соответствующих
     */
    private volatile static Map<ObjectOutputStream, UserInfo> userStreamMap = new HashMap<>();

    /**
     * Задание соответствия текущего выходного потока и информации о пользователе
     */
    public static void addClientOut(ObjectOutputStream out){
        UserInfo userInfo = userStreamMap.get(out);
        if (userInfo == null){
            userInfo = new UserInfo(getAnonymousName());
        }
        userStreamMap.putIfAbsent(out, userInfo);
    }

    /**
     * Задание пользователю дефолтного имени
     */
    private static String getAnonymousName() {
        return "anonymous#" + anonUserCount++;
    }

    /**
     * Проверка занято-ли ли имя пользователя
     */
    public static boolean userExist(String name){
        return loginSet.contains(name);
    }

    /**
     * Добавление нового пользователя в список занятых имён
     */
    public static void addNewUser(String username){
        loginSet.add(username);
    }
}
