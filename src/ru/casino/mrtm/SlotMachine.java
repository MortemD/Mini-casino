package ru.casino.mrtm;

import java.util.*; //Импортируем библиотеку со сканером, ArrayList и рандомом

class SlotMachine {
    //Переменные экземпляра

    private final Random casinoBackWork = new Random(); //Генератор случайных чисел, без него никуда
    private final Scanner input = new Scanner(System.in);
    //Сканер input. Переменную final нельзя изменить. Private переменная видна внутри класса
    private int x = 0; //Переменная, содержащая количество иксов
    private int oneSpinCost = 0; //Переменная, содержащая цену одной прокрути автомата
    private final char[] kit = new char[] {'O', 'B', 'M','A' ,'N', '?', '!'}; //Массив с элементами, которые будут выпадать
    int[] dropped = new int[5]; //Массив содержит то, что нам выпадает при spin()
    private short playerAttempt = 0; //Количество попыток игрок / количество сделанных spin
    //Методы

    void startGameInfo(int convertedYours) {
        //Выводим начальную информацию об игровом режиме.
        //Получаем стартовые данные от игрока.
        System.out.println(""" 
                \s
                Игровой автомат\s
                \s
                Вам необходимо выбить на игровом автомате 5 одинаковых символов \s
                В таком случае вы получите: стоимость одного спина * 2 * коэффициент \s
                Если вам выпадет 4 одинаковых элемента подряд, то ваш коэффициент увеличится на 5 \s
                Иногда вам могут попасться: "МЕГА ВЫИГРЫШ" или "СУПЕР КОЭФФИЦИЕНТЫ" \s
                Перед началом выберите сам игровой автомат, опираясь на их характеристику: \s
                \s
                "Новичок" - 1 спин стоит 10 уие, стартовый коэффициент х1 \s
                "Бывалый" - 1 спин стоит 20 уие, стартовый коэффициент х2 \s
                "Средняк" - 1 спин стоит 30 уие, стартовый коэффициент х3 \s
                "Пабло" - 1 спин стоит 40 уие, стартовый коэффициент х4 \s
                "Президент" - 1 спин стоит 50 уие, стартовый коэффициент х5 \s
                "Drake" - 1 спин стоит 100 уие, стартовый коэффициент х10 \s
                "Фёдор_Щигорев" - 1 спин стоит 5555 уие, стартовый коэффициент х500, всё или ничего \s
                \s
                Выберите автомат, на котором вы будете играть:
                """);
        machineSelection(); //Метод для выбора автомата

        System.out.println("\nВаш начальный баланс: " + convertedYours + "\nВаши начальные иксы: "
                + x + "\nСтоимость spin на игровом автомате: " + oneSpinCost);
        //Выводим информацию о настройках игрока
    }

    int spin(int convertedYours) {

        playerAttempt++; //Сделан 1 spin, увеличиваем соответствующую переменную

        //Получаем на вход уие - общее число денег игрока
        convertedYours -= oneSpinCost; //Вычитаем уие за один прокрут на автомате

        superX(); //Проверяем, вдруг сейчас тот момент, когда нам выпадут "Супер коэффициенты"

        for (int i = 0; i < 5; i++) dropped[i] = (casinoBackWork.nextInt(0, 7));
        /*
        Сам механизм по выпаданию рандомных значений. Выпадает рандомное число, сохраняется в массив.
        Выводится соответствующий символ. Рандом на значениях длины массива с элементами, которые выпадают.
        */

        justBeautyOut(); //Выводим, что выпадает на игровом автомате

        convertedYours = profitCheck(dropped, convertedYours);
        //Реализуем метод по проверке выпавших значений на содержание выигрыша

        balance(convertedYours); //Показываем игроку его баланс по итогу спина

        return convertedYours; //Возвращает сумму, которая остаётся после одной прокрутки
    }

    private void balance(int convertedYours) {
        //Метод для отображения баланса
        System.out.println("\nВаш баланс:" + convertedYours + " уие");
        System.out.println("Количество иксов:" + x);
    }

    private int profitCheck(int[] dr, int convertedYours) {
        //Массивы с выигрышной комбинацией
        int[] temporaryStorageFirst = new int[] {dr[2], dr[2], dr[2], dr[2], dr[2]};
        //Случай когда все 5 одинаковые, вместо 2 может быть любое от 0 по 4
        int[] temporaryStorageSecond = new int[] {dr[2], dr[2], dr[2], dr[2], dr[4]};
        //Случай, когда 4 одинаковые, а последнее им не равно
        int[] temporaryStorageThird = new int[] {dr[0], dr[2], dr[2], dr[2], dr[2]};
        //Случай, когда 4 одинаковые, а первое им не равно

        //Проверка входящего в метод массива на его "выигрышность"
        if (Arrays.equals(dr, temporaryStorageFirst)) { //5 одинаковых подряд
            //method returns true if the two specified arrays of objects are equal to one another.
            System.out.println("\n5 одинаковых подряд ! Вам очень повезло");
            convertedYours = convertedYours + oneSpinCost * 2 * x; //стоимость одного спина * 2 * коэффициент
            convertedYours = megaWin(convertedYours); //Иногда может произойти момент, когда случается "Мега выигрыш"
        }
        else if (Arrays.equals(dr, temporaryStorageSecond) || Arrays.equals(dr, temporaryStorageThird)) {
            //4 одинаковых подряд
            System.out.println("\n4 одинаковых подряд! Вам сегодня везёт");
            x += 5; //Увеличиваем иксы
        }

        //Случай, когда нам вообще не везёт
        else System.out.println("\nНичего страшного. В следующий раз вам повезёт !");

        return convertedYours; //Возвращаем нашу итоговую сумму
    }

    private void superX() {
        //Может попасться ситуация с высокими коэффициентами. Ваши иксы увеличиваются на 100 при этом событии.
        //Это случается редко, пусть всего максимум 2 раза.
        //Если выпадает выигрышная комбинация, то вы получаете крупную сумму.
        if(playerAttempt == casinoBackWork.nextInt(10, 100) || playerAttempt == casinoBackWork.nextInt(100, 200) )
        {
            System.out.println("Супер коэффициент! Ваши иксы увеличены на 100 !"); //Вывод соответствующего сообщения
            x = x + 100; //Увеличиваем иксы
        }
    }

    private void machineSelection() { //Для выбора игрового автомата
        System.out.print(">>>");//Строка делает понятнее, куда вводить название
        switch (input.next()) { //The New switch Expression java 13
            //Внутри скобок switch() устанавливаем ввод с консоли
            //С помощью switch-case-default присваиваем значения следующим переменным: иксы и стоимость одного spin соответственно
            case "Новичок" -> {
                x = 1;
                oneSpinCost = 10;
            }
            case "Бывалый" -> {
                x = 2;
                oneSpinCost = 20;
            }
            case "Средняк" -> {
                x = 3;
                oneSpinCost = 30;
            }
            case "Пабло" -> {
                x = 4;
                oneSpinCost = 40;
            }
            case "Президент" -> {
                x = 5;
                oneSpinCost = 50;
            }
            case "Drake" -> {
                x = 10;
                oneSpinCost = 100;
            }
            case "Фёдор_Щигорев" -> {
                x = 500;
                oneSpinCost = 5555;
            }
            default -> {
                System.out.println("Вы ошибочно ввели название! Попробуйте ещё раз !"); //Если ввели неправильно
                machineSelection(); //Переходим снова к методу
            }
        }
    }

    private void justBeautyOut() { //Просто вывод тех символов, которые нам выпадают
        System.out.print("""
                 \s
                 +———+ +———+ +———+ +———+ +———+ \s
                 | 1 | | 2 | | 3 | | 4 | | 5 | \s
                 |———| |———| |———| |———| |———| \s
                """);
        for (int i = 0; i < 5; i++) System.out.print(" | " + kit[dropped[i]] + " |");
        System.out.println("\n +———+ +———+ +———+ +———+ +———+ ");
    }

    private int megaWin(int convertedYours){
        //Метод, реализующий "Мега выигрыш".
        // В случае, если у вас выпали все 5 одинаковых элементов и число ваших попыток равно некоему числу, то у вас
        //возможен мега выигрыш. Его вероятность очень маленькая.
        int luckMoment = casinoBackWork.nextInt(1, 10) + 10;
        //Переменная, содержащая данные о моменте, в который у игрока будет мега выигрыш

        if(luckMoment % playerAttempt == 5) {
            //Проверка на мега выигрыш. Если остаток от деления luckMoment на попытку игрока равен 5.
            //Вероятность мала, и возможна "вначале" игры. Служит для заманивания игроков в казино.
            System.out.println("МЕГА ВЫИГРЫШ!"); //Уведомление
            convertedYours = convertedYours + 2 * x * casinoBackWork.nextInt(1000, 10_000); //Мега выигрыш
        }
        return convertedYours; //Возвращаем сумму денег игрока после всего этого
    }
}