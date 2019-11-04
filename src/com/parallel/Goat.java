package com.parallel;

import java.util.Scanner;

public class Goat {
    private static int goats = 0;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        int tableSize = getNumber();

        var goat1 = createGoat(0, tableSize);
        var goat2 = createGoat(tableSize, 0);

        goat1.setOpponent(goat2);
        goat2.setOpponent(goat1);

        Thread t1 = new Thread(new GoatRunnable(goat1));
        Thread t2 = new Thread(new GoatRunnable(goat2));

        t1.start();
        t2.start();
    }

    private static int getNumber() {
        System.out.println("Milyen hosszu legyen a pallo? (pozitiv egesz szamot adj meg)");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    private static GoatEntity createGoat(int startPosition, int targetPosition) {
        var goat = new GoatEntity(goats, startPosition, targetPosition);
        goats++;
        return goat;
    }

}


