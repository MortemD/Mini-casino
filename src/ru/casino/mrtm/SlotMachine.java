package ru.casino.mrtm;

import java.util.*; //����������� ���������� �� ��������, ArrayList � ��������

class SlotMachine {
    //���������� ����������

    private final Random casinoBackWork = new Random(); //��������� ��������� �����, ��� ���� ������
    private final Scanner input = new Scanner(System.in);
    //������ input. ���������� final ������ ��������. Private ���������� ����� ������ ������
    private int x = 0; //����������, ���������� ���������� �����
    private int oneSpinCost = 0; //����������, ���������� ���� ����� �������� ��������
    private final char[] kit = new char[] {'O', 'B', 'M','A' ,'N', '?', '!'}; //������ � ����������, ������� ����� ��������
    int[] dropped = new int[5]; //������ �������� ��, ��� ��� �������� ��� spin()
    private short playerAttempt = 0; //���������� ������� ����� / ���������� ��������� spin
    //������

    void startGameInfo(int convertedYours) {
        //������� ��������� ���������� �� ������� ������.
        //�������� ��������� ������ �� ������.
        System.out.println(""" 
                \s
                ������� �������\s
                \s
                ��� ���������� ������ �� ������� �������� 5 ���������� �������� \s
                � ����� ������ �� ��������: ��������� ������ ����� * 2 * ����������� \s
                ���� ��� ������� 4 ���������� �������� ������, �� ��� ����������� ���������� �� 5 \s
                ������ ��� ����� ���������: "���� �������" ��� "����� ������������" \s
                ����� ������� �������� ��� ������� �������, �������� �� �� ��������������: \s
                \s
                "�������" - 1 ���� ����� 10 ���, ��������� ����������� �1 \s
                "�������" - 1 ���� ����� 20 ���, ��������� ����������� �2 \s
                "�������" - 1 ���� ����� 30 ���, ��������� ����������� �3 \s
                "�����" - 1 ���� ����� 40 ���, ��������� ����������� �4 \s
                "���������" - 1 ���� ����� 50 ���, ��������� ����������� �5 \s
                "Drake" - 1 ���� ����� 100 ���, ��������� ����������� �10 \s
                "Ը���_�������" - 1 ���� ����� 5555 ���, ��������� ����������� �500, �� ��� ������ \s
                \s
                �������� �������, �� ������� �� ������ ������:
                """);
        machineSelection(); //����� ��� ������ ��������

        System.out.println("\n��� ��������� ������: " + convertedYours + "\n���� ��������� ����: "
                + x + "\n��������� spin �� ������� ��������: " + oneSpinCost);
        //������� ���������� � ���������� ������
    }

    int spin(int convertedYours) {

        playerAttempt++; //������ 1 spin, ����������� ��������������� ����������

        //�������� �� ���� ��� - ����� ����� ����� ������
        convertedYours -= oneSpinCost; //�������� ��� �� ���� ������� �� ��������

        superX(); //���������, ����� ������ ��� ������, ����� ��� ������� "����� ������������"

        for (int i = 0; i < 5; i++) dropped[i] = (casinoBackWork.nextInt(0, 7));
        /*
        ��� �������� �� ��������� ��������� ��������. �������� ��������� �����, ����������� � ������.
        ��������� ��������������� ������. ������ �� ��������� ����� ������� � ����������, ������� ��������.
        */

        justBeautyOut(); //�������, ��� �������� �� ������� ��������

        convertedYours = profitCheck(dropped, convertedYours);
        //��������� ����� �� �������� �������� �������� �� ���������� ��������

        balance(convertedYours); //���������� ������ ��� ������ �� ����� �����

        return convertedYours; //���������� �����, ������� ������� ����� ����� ���������
    }

    private void balance(int convertedYours) {
        //����� ��� ����������� �������
        System.out.println("\n��� ������:" + convertedYours + " ���");
        System.out.println("���������� �����:" + x);
    }

    private int profitCheck(int[] dr, int convertedYours) {
        //������� � ���������� �����������
        int[] temporaryStorageFirst = new int[] {dr[2], dr[2], dr[2], dr[2], dr[2]};
        //������ ����� ��� 5 ����������, ������ 2 ����� ���� ����� �� 0 �� 4
        int[] temporaryStorageSecond = new int[] {dr[2], dr[2], dr[2], dr[2], dr[4]};
        //������, ����� 4 ����������, � ��������� �� �� �����
        int[] temporaryStorageThird = new int[] {dr[0], dr[2], dr[2], dr[2], dr[2]};
        //������, ����� 4 ����������, � ������ �� �� �����

        //�������� ��������� � ����� ������� �� ��� "������������"
        if (Arrays.equals(dr, temporaryStorageFirst)) { //5 ���������� ������
            //method returns true if the two specified arrays of objects are equal to one another.
            System.out.println("\n5 ���������� ������ ! ��� ����� �������");
            convertedYours = convertedYours + oneSpinCost * 2 * x; //��������� ������ ����� * 2 * �����������
            convertedYours = megaWin(convertedYours); //������ ����� ��������� ������, ����� ��������� "���� �������"
        }
        else if (Arrays.equals(dr, temporaryStorageSecond) || Arrays.equals(dr, temporaryStorageThird)) {
            //4 ���������� ������
            System.out.println("\n4 ���������� ������! ��� ������� ����");
            x += 5; //����������� ����
        }

        //������, ����� ��� ������ �� ����
        else System.out.println("\n������ ���������. � ��������� ��� ��� ������ !");

        return convertedYours; //���������� ���� �������� �����
    }

    private void superX() {
        //����� ��������� �������� � �������� ��������������. ���� ���� ������������� �� 100 ��� ���� �������.
        //��� ��������� �����, ����� ����� �������� 2 ����.
        //���� �������� ���������� ����������, �� �� ��������� ������� �����.
        if(playerAttempt == casinoBackWork.nextInt(10, 100) || playerAttempt == casinoBackWork.nextInt(100, 200) )
        {
            System.out.println("����� �����������! ���� ���� ��������� �� 100 !"); //����� ���������������� ���������
            x = x + 100; //����������� ����
        }
    }

    private void machineSelection() { //��� ������ �������� ��������
        System.out.print(">>>");//������ ������ ��������, ���� ������� ��������
        switch (input.next()) { //The New switch Expression java 13
            //������ ������ switch() ������������� ���� � �������
            //� ������� switch-case-default ����������� �������� ��������� ����������: ���� � ��������� ������ spin ��������������
            case "�������" -> {
                x = 1;
                oneSpinCost = 10;
            }
            case "�������" -> {
                x = 2;
                oneSpinCost = 20;
            }
            case "�������" -> {
                x = 3;
                oneSpinCost = 30;
            }
            case "�����" -> {
                x = 4;
                oneSpinCost = 40;
            }
            case "���������" -> {
                x = 5;
                oneSpinCost = 50;
            }
            case "Drake" -> {
                x = 10;
                oneSpinCost = 100;
            }
            case "Ը���_�������" -> {
                x = 500;
                oneSpinCost = 5555;
            }
            default -> {
                System.out.println("�� �������� ����� ��������! ���������� ��� ��� !"); //���� ����� �����������
                machineSelection(); //��������� ����� � ������
            }
        }
    }

    private void justBeautyOut() { //������ ����� ��� ��������, ������� ��� ��������
        System.out.print("""
                 \s
                 +���+ +���+ +���+ +���+ +���+ \s
                 | 1 | | 2 | | 3 | | 4 | | 5 | \s
                 |���| |���| |���| |���| |���| \s
                """);
        for (int i = 0; i < 5; i++) System.out.print(" | " + kit[dropped[i]] + " |");
        System.out.println("\n +���+ +���+ +���+ +���+ +���+ ");
    }

    private int megaWin(int convertedYours){
        //�����, ����������� "���� �������".
        // � ������, ���� � ��� ������ ��� 5 ���������� ��������� � ����� ����� ������� ����� ������� �����, �� � ���
        //�������� ���� �������. ��� ����������� ����� ���������.
        int luckMoment = casinoBackWork.nextInt(1, 10) + 10;
        //����������, ���������� ������ � �������, � ������� � ������ ����� ���� �������

        if(luckMoment % playerAttempt == 5) {
            //�������� �� ���� �������. ���� ������� �� ������� luckMoment �� ������� ������ ����� 5.
            //����������� ����, � �������� "�������" ����. ������ ��� ����������� ������� � ������.
            System.out.println("���� �������!"); //�����������
            convertedYours = convertedYours + 2 * x * casinoBackWork.nextInt(1000, 10_000); //���� �������
        }
        return convertedYours; //���������� ����� ����� ������ ����� ����� �����
    }
}