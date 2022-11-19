package ru.casino.mrtm;

public class Chief {
    //Переменные экземпляра
    private final static AssistantChief assistant = new AssistantChief();
    //Объект класса AssistantChief, который будет содержать весь вспомогательный код

    //Методы
    public static void main(String[] args) {
        assistant.helloMessage(); //Вызов метода с приветственным сообщением
        assistant.terminal(); //Игрок переходит в терминал для первичного пополнения своего счёта
        assistant.gameplay(); //Вся игра содержится внутри этого метода
    }
}