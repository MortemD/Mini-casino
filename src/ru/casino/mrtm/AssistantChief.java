package ru.casino.mrtm;

import java.util.*; //������ ��� ������ �������

class AssistantChief {
    //���������� ����������
    private final Scanner assistant = new Scanner(System.in); //������ ��� ���������� �������� �� �������
    private int currency = 0; //���������� �������� ����� - ��� ���� ������ � ���
    private int casinoDebt = 0; //���������� ������� �������� ������ � ����� ������ � ��
    private boolean shouldItStop = false; //����������, ������� ����������� ��� � ������� � ������. �������� ������ ������ �� ������


    //������
    void helloMessage() {//����� �������� ����� ���������, ������� ����� ����� �����, ��� ������ ������� � ����
        System.out.println("""
                \s
                ������������! ����� ���������� � ������ ������ KINDEST PEOPLE ! \s
                ��� ������������� �� ����� ��� �������� ������� ������, ����� ������ � �������� �������� �����! \s
                ����� ������� ��������� � ��������� � �������� ��� - �������� ������ � ����� ������. \s
                � ����� �� ������� ������� �����, � ������� ���������� ������. \s
                � �������, ����, ������ ��� ����� �����! \s
                """);
    }

    void terminal() { //���������, ������� ���������� ��� ���������� ������ ����� � ������
        System.out.println("\n��� ������: " + currency + "\n�� ����������� ���������� ������ KINDEST PEOPLE."); //������� ������ ������
        System.out.println(""" 
                ����� ��������� ����� ������:\s
                ���� >>> 10 �������� ������ �������������� � 1 �������� ������� ������� \s
                ������ �������� ��������!\s
                � ������, ������� ����� � �� (�������� �����):\s
                """); //���� ���������
        System.out.print(">>>"); //������� ������ ����������, ���� ����� ��������� ���� ��������

        inputCheck(); //����� ��� �������� ��������� �������
    }

    private void inputCheck() { //����� ��� �������� ����� � ������ terminal()
        String temporaryForCheckingInput; //��������� ����������
        while (true) { //����������� ����� ���
            try { //����������� �������� ����� ����
                temporaryForCheckingInput = assistant.next();//������ � ���� ��������� ���������� ��������

                currency += Integer.parseInt(temporaryForCheckingInput) / 10; //���� �����������
                if (Integer.parseInt(temporaryForCheckingInput) <= 0) { //�������� �� ��, ������� �� ����� ������ ����
                    System.out.println("� ��� �������� � ������ �����"); //�����������
                    System.out.print(">>>"); //������� ������ ����������, ���� ����� ��������� ���� ��������
                    inputCheck();//������������
                }
                break; //����� �� ����� while(true)
            } catch (NumberFormatException e) { //����� ������
                System.out.println("�������� � ������ �����. ���������� ��� ���! ");//�����������
                System.out.print(">>>"); //��������� ���� ������.
            }
        }
    }

    void gameplay(){
        if (currency * 10 <= 0) casinoDebt = Math.abs(currency * 10); //������� ����� �����
        else casinoDebt = 0; //� ������ ���� ��� ������

        System.out.println("\n��� ������: " + currency + " ���");//����� �������
        System.out.println("��� ���� ������: " + casinoDebt / 10 + " ���");//����� �����
        System.out.println("""
                ������ �������� ��, ��� ��� ����������.\s
                �� �����: ������� �������� ��� �������.\s
                �� ����� ������ "���������" � ���������, ���� ����� ������ ��������� ���� ������.\s
                ����� �� ������ ����� �����, �� ��� �� ������.\s
                >>>�������_��������
                >>>�������
                >>>��������
                >>>�������_��_������
                """); //��, ��� ����� �������
        gameTaker(); //����� ��� ������������� ������
    }

    private void gameTaker(){
        System.out.print(">>>");//������ ������ ��������, ���� ������� ��������
        switch (assistant.next()) {
            //������ ������ switch() ������������� ���� � �������
            //� ������� switch-case-default ��������� � ��������������� ������
            case "�������_��������" -> newGameSlotMachines(); //����� � �������� ����������
            case "�������" -> newGameRoulette(); //����� � ��������
            case "��������" -> {
                terminal(); //����������� � ���������
                gameplay(); //����������� � ���� ������ ������
            }
            case "�������_��_������" -> goingHome(); //����� �� ������
            default -> {
                System.out.println("�� �������� ����� ��������! ���������� ��� ��� !"); //���� ����� �����������
                gameTaker(); //��������� ����� � ������
            }
        }
    }

    private void goingHome(){ //����� ��� ���������� ������ �� ������
        System.out.println("�� ���� �� ������ � ����� ����� �������� ������ " + currency * 10 + " ��"); //�����������
        if (casinoDebt > 0)
            System.out.println("��� ���� ������ " + casinoDebt + "��. \n������, �� ��� � ����� ������ ������" );
        //����������� � ����� ����� ������

        System.exit(0); //��� ��������� ����
    }

    private boolean loopExit(SlotMachine slotMachine) {
        System.out.println("""
        ������? \s
        >>>�� \s
        >>>��� \s
        """); //����������� � ������� ���������� ����
        System.out.print(">>>"); //��������� ���� ������
        switch (assistant.next()){ //�������������� switch case ��� ��� ��
            case "��" -> { //���� ���������� ������
                System.out.println("�������!"); //�����������
                return false; //���������� ��������
            }
            case "���" -> { //���� ���������� ������
                System.out.println("""
                ������� �������? \s
                >>>�� \s
                >>>��� \s
                """); //����������� � ������� ������� �������
                System.out.print(">>>"); //��������� ���� ������
                switch (assistant.next()){
                    case "��" -> slotMachine.startGameInfo(currency); //���� ������ �������, �� ��������� � ������ � ������� ���������
                    case "���" -> { //���� �� ������ �������, �� ������� �� ������
                        System.out.println("�� �������� �� �������� ������"); //�����������
                        return true; //���������� ��������
                    }
                    default -> { //����������
                        System.out.println("�� �������� ����� ��������! ���������� ��� ��� !"); //���� ����� �����������
                        loopExit(slotMachine); //����� � ����
                    }
                }
            }
            default -> { //����������
                System.out.println("�� �������� ����� ��������! ���������� ��� ��� !"); //���� ����� �����������
                loopExit(slotMachine); //����� � ����
            }
        }
        return false; //�� ��������� ������� false

    }

    private boolean loopExit() {
        System.out.println("""
        ������? \s
        >>>�� \s
        >>>��� \s
        """); //����������� � ������� ���������� ����
        System.out.print(">>>"); //��������� ���� ������
        switch (assistant.next()){ //�������������� switch case ��� ��� ��
            case "��" -> { //���� ���������� ������
                System.out.println("�������!"); //�����������
                return false; //���������� ��������
            }
            case "���" -> {
                System.out.println("�� �������� �� �������� ������"); //�����������
                return true; //���������� ��������
            }
            default -> { //����������
                System.out.println("�� �������� ����� ��������! ���������� ��� ��� !"); //���� ����� �����������
                loopExit(); //����� � ����
            }
        }
        return false; //�� ��������� ������� false

    }
    private void newGameSlotMachines() {
        SlotMachine slotMachine = new SlotMachine(); //������ ������ SlotMachine
        slotMachine.startGameInfo(currency); //�������� ��������� ����������
        while(!shouldItStop || currency <= 0) { //���� ������ ���� � ��� ������� ��������
            if (currency <= 0) { //���� ����� ���
                System.out.println("� ��� ������������ �����! \n�� �������� �� �������� ������!"); //����������
                System.out.println("������������� � �������� � ��������� ���� ����!"); //����������
                break; //������� �� ����� while
            }

            shouldItStop = loopExit(slotMachine); //��������� �� ������� ���������� ����

            if(!shouldItStop) currency = slotMachine.spin(currency); //�������, ������� "������" ��������
        }
        shouldItStop = false;
        gameplay(); //����������� � ���� ������ ������
    }

    private void newGameRoulette() {
        Roulette roulette = new Roulette();
        roulette.welcomeMessage();
        while(!shouldItStop || currency <= 0) { //���� ������ ���� � ��� ������� ��������
            if (currency <= 0) { //���� ����� ���
                System.out.println("� ��� ������������ �����! \n�� �������� �� �������� ������!"); //����������
                System.out.println("������������� � �������� � ��������� ���� ����!"); //����������
                break; //������� �� ����� while
            }

            shouldItStop = loopExit(); //��������� �� ������� ���������� ����

            if(!shouldItStop) {
                currency += roulette.placeBet(); //���������� � ���������� �������, ����� ��� ��� ����� � �����
            }
        }
        shouldItStop = false; //������ �������� ����������
        gameplay(); //����������� � ���� ������ ������
    }
}