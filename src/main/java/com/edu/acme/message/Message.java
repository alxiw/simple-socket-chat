package com.edu.acme.message;

import com.edu.acme.Command;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, описывающий вид сообщения, которыми обмениваются сервер с клиентами
 */
public abstract class Message implements Serializable {
    /**
     * Поле текст сообщения
     */
    protected String text;
    /**
     * Поле время отправки сообщения
     */
    protected String time;
    /**
     * Поле поток вывода, привязанный к определённому клиенту
     */
    protected transient ObjectOutputStream out;

    /**
     * Конструктор - создание сообщения
     */
    public Message(String text) {
        this.text = text;
        this.time = new Date().toString();
    }

    /**
     * Выявление команды из сообщения
     */
    public abstract Command getCommand();

    /**
     * Установка теущей даты отправки сообщения
     */
    public void setCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
    }

    /**
     * Обработка сообщения
     */
    public abstract void process(ObjectOutputStream out);

    /**
     * Вывод текста сообщения с добавлением к нему даты отправки
     */
    @Override
    public String toString() {
        return "[" + time + "]: " + text;
    }
}