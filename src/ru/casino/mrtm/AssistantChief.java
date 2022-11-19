package ru.casino.mrtm;

import java.util.*; //Строка для работы сканера

class AssistantChief {
    //Переменные экземпляра
    private final Scanner assistant = new Scanner(System.in); //Сканер для считывания значений из консоли
    private int currency = 0; //Переменная содержит число - все ваши деньги в уие
    private int casinoDebt = 0; //Переменная которая содержит данные о долге игрока в рр
    private boolean shouldItStop = false; //Переменная, которая понадобится нам в методах с играми. Означает момент выхода из режима


    //Методы
    void helloMessage() {//Метод содержит вывод сообщения, которое игрок видит сразу, как только заходит в игру
        System.out.println("""
                \s
                Здравствуйте! Добро пожаловать в онлайн казино KINDEST PEOPLE ! \s
                Вам предоставлено на выбор два основных игровых режима, новые режимы и автоматы появятся скоро! \s
                Перед началом перейдите к терминалу и получите уие - основную валюту в нашем казино. \s
                А далее вы сможете выбрать режим, в который планируете играть. \s
                И помните, дети, казино это очень плохо! \s
                """);
    }

    void terminal() { //Сообщение, которое появляется при пополнении своего счёта в казино
        System.out.println("\nВаш баланс: " + currency + "\nВы пользуетесь терминалом казино KINDEST PEOPLE."); //Выводим баланс игрока
        System.out.println(""" 
                Перед внесением вашей валюты:\s
                Курс >>> 10 реальных рублей конвертируется в 1 условную игровую единицу \s
                Иногда возможна комиссия!\s
                А теперь, внесите сумму в рр (реальные рубли):\s
                """); //Само сообщение
        System.out.print(">>>"); //Строчка просто показывает, куда нужно вписывать наше значение

        inputCheck(); //Метод для проверки введённого игроком
    }

    private void inputCheck() { //Метод для проверки ввода в методе terminal()
        String temporaryForCheckingInput; //Локальная переменная
        while (true) { //Проделываем много раз
            try { //Проделываем основную часть кода
                temporaryForCheckingInput = assistant.next();//Вводим в нашу временную переменную значение

                currency += Integer.parseInt(temporaryForCheckingInput) / 10; //Сама конвертация
                if (Integer.parseInt(temporaryForCheckingInput) <= 0) { //Проверка на то, введено ли число меньше нуля
                    System.out.println("У вас проблемы с вводом числа"); //Уведомление
                    System.out.print(">>>"); //Строчка просто показывает, куда нужно вписывать наше значение
                    inputCheck();//Возвращаемся
                }
                break; //Выход из цикла while(true)
            } catch (NumberFormatException e) { //Ловим ошибку
                System.out.println("Проблемы с вводом числа. Попробуйте ещё раз! ");//Уведомление
                System.out.print(">>>"); //Указываем куда писать.
            }
        }
    }

    void gameplay(){
        if (currency * 10 <= 0) casinoDebt = Math.abs(currency * 10); //Подсчёт суммы долга
        else casinoDebt = 0; //В случае если нет долгов

        System.out.println("\nВаш баланс: " + currency + " уие");//Вывод баланса
        System.out.println("Ваш долг казино: " + casinoDebt / 10 + " уие");//Вывод долга
        System.out.println("""
                Теперь выберите то, что вас интересует.\s
                На выбор: игровые автоматы или рулетка.\s
                Вы также можете "обратится" к терминалу, если вдруг хотите пополнить свой баланс.\s
                Также вы можете пойти домой, мы вас не держим.\s
                >>>Игровые_автоматы
                >>>Рулетка
                >>>Терминал
                >>>Убежать_из_казино
                """); //То, что можно выбрать
        gameTaker(); //Метод для осуществления выбора
    }

    private void gameTaker(){
        System.out.print(">>>");//Строка делает понятнее, куда вводить название
        switch (assistant.next()) {
            //Внутри скобок switch() устанавливаем ввод с консоли
            //С помощью switch-case-default переходим в соответствующие методы
            case "Игровые_автоматы" -> newGameSlotMachines(); //Режим с игровыми автоматами
            case "Рулетка" -> newGameRoulette(); //Режим с рулеткой
            case "Терминал" -> {
                terminal(); //Возвращение к терминалу
                gameplay(); //Возвращение к меню выбора режима
            }
            case "Убежать_из_казино" -> goingHome(); //Выход из казино
            default -> {
                System.out.println("Вы ошибочно ввели название! Попробуйте ещё раз !"); //Если ввели неправильно
                gameTaker(); //Переходим снова к методу
            }
        }
    }

    private void goingHome(){ //Метод для реализации выхода из казино
        System.out.println("Вы ушли из казино с вашим общим балансом равным " + currency * 10 + " рр"); //Уведомление
        if (casinoDebt > 0)
            System.out.println("Ваш долг казино " + casinoDebt + "рр. \nЗнайте, мы его в любом случае заберём" );
        //Уведомление о долге перед казино

        System.exit(0); //Для остановки игры
    }

    private boolean loopExit(SlotMachine slotMachine) {
        System.out.println("""
        Играем? \s
        >>>Да \s
        >>>Нет \s
        """); //Уведомление о желании прекратить игру
        System.out.print(">>>"); //Указываем куда писать
        switch (assistant.next()){ //Организовываем switch case под это всё
            case "Да" -> { //Если продолжаем играть
                System.out.println("Отлично!"); //Уведомление
                return false; //Возвращаем значение
            }
            case "Нет" -> { //Если прекращаем играть
                System.out.println("""
                Сменить автомат? \s
                >>>Да \s
                >>>Нет \s
                """); //Уведомление о желании сменить автомат
                System.out.print(">>>"); //Указываем куда писать
                switch (assistant.next()){
                    case "Да" -> slotMachine.startGameInfo(currency); //Если меняем автомат, то переходим к методу с выбором автоматов
                    case "Нет" -> { //Если не меняем автомат, то выходим из режима
                        System.out.println("Вы выходите из игрового режима"); //Уведомление
                        return true; //Возвращаем значение
                    }
                    default -> { //Стандартно
                        System.out.println("Вы ошибочно ввели название! Попробуйте ещё раз !"); //Если ввели неправильно
                        loopExit(slotMachine); //Снова в цикл
                    }
                }
            }
            default -> { //Стандартно
                System.out.println("Вы ошибочно ввели название! Попробуйте ещё раз !"); //Если ввели неправильно
                loopExit(slotMachine); //Снова в цикл
            }
        }
        return false; //По умолчанию выводим false

    }

    private boolean loopExit() {
        System.out.println("""
        Играем? \s
        >>>Да \s
        >>>Нет \s
        """); //Уведомление о желании прекратить игру
        System.out.print(">>>"); //Указываем куда писать
        switch (assistant.next()){ //Организовываем switch case под это всё
            case "Да" -> { //Если продолжаем играть
                System.out.println("Отлично!"); //Уведомление
                return false; //Возвращаем значение
            }
            case "Нет" -> {
                System.out.println("Вы выходите из игрового режима"); //Уведомление
                return true; //Возвращаем значение
            }
            default -> { //Стандартно
                System.out.println("Вы ошибочно ввели название! Попробуйте ещё раз !"); //Если ввели неправильно
                loopExit(); //Снова в цикл
            }
        }
        return false; //По умолчанию выводим false

    }
    private void newGameSlotMachines() {
        SlotMachine slotMachine = new SlotMachine(); //Объект класса SlotMachine
        slotMachine.startGameInfo(currency); //Получаем стартовую информацию
        while(!shouldItStop || currency <= 0) { //Пока деньги есть и нет желания выходить
            if (currency <= 0) { //Если денег нет
                System.out.println("У вас недостаточно денег! \nВы выходите из игрового режима!"); //Уведомляем
                System.out.println("Направляйтесь в терминал и пополните свой счёт!"); //Уведомляем
                break; //Выходим из цикла while
            }

            shouldItStop = loopExit(slotMachine); //Проверяем на желание остановить игру

            if(!shouldItStop) currency = slotMachine.spin(currency); //Строчка, которая "крутит" автоматы
        }
        shouldItStop = false;
        gameplay(); //Возвращение к меню выбора режима
    }

    private void newGameRoulette() {
        Roulette roulette = new Roulette();
        roulette.welcomeMessage();
        while(!shouldItStop || currency <= 0) { //Пока деньги есть и нет желания выходить
            if (currency <= 0) { //Если денег нет
                System.out.println("У вас недостаточно денег! \nВы выходите из игрового режима!"); //Уведомляем
                System.out.println("Направляйтесь в терминал и пополните свой счёт!"); //Уведомляем
                break; //Выходим из цикла while
            }

            shouldItStop = loopExit(); //Проверяем на желание остановить игру

            if(!shouldItStop) {
                currency += roulette.placeBet(); //Записываем в переменную выигрыш, также это сам метод с игрой
            }
        }
        shouldItStop = false; //Меняем значение переменной
        gameplay(); //Возвращение к меню выбора режима
    }
}