package ru.casino.mrtm;

public class Chief {
    //���������� ����������
    private final static AssistantChief assistant = new AssistantChief();
    //������ ������ AssistantChief, ������� ����� ��������� ���� ��������������� ���

    //������
    public static void main(String[] args) {
        assistant.helloMessage(); //����� ������ � �������������� ����������
        assistant.terminal(); //����� ��������� � �������� ��� ���������� ���������� ������ �����
        assistant.gameplay(); //��� ���� ���������� ������ ����� ������
    }
}