package com.edu.acme.message;

import com.edu.acme.Command;
import java.io.ObjectOutputStream;

/**
 * �����, ����������� ����������� ��������� �� ������� � �������
 */
public class ServerMessage extends Message {
    /**
     * ����������� - �������� ������ ������������ ���������
     * @param text ����� ���������
     */
    public ServerMessage(String text) {
        super(text);
    }

    /**
     * ������ ���� ������� � ���������
     */
    @Override
    public Command getCommand() {
        return null;
    }

    /** ��������� ���������
     * @param out - ����� ������ �� ������� � �������
     */
    @Override
    public void process(ObjectOutputStream out) {

    }

}