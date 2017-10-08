package com.edu.acme.message;

import com.edu.acme.Command;
import java.io.ObjectOutputStream;

/**
 * Класс, описывающий функционные сообщения от сервера к клиенту
 */
public class ServerMessage extends Message {
    /**
     * Конструктор - создание нового функционного сообщения
     * @param text текст сообщения
     */
    public ServerMessage(String text) {
        super(text);
    }

    /**
     * Геттер поля команды в сообщении
     */
    @Override
    public Command getCommand() {
        return null;
    }

    /** Обработка сообщения
     * @param out - поток вывода от сервера к клиенту
     */
    @Override
    public void process(ObjectOutputStream out) {

    }

}