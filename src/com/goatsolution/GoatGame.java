package com.goatsolution;

import java.util.concurrent.ThreadLocalRandom;

public class GoatGame {
    private int boardLen = 10;
    private int pos = boardLen / 2;

    public static void main(String[] args) throws Exception {
        var game = new GoatGame();

        Thread goat1 = new Thread(newGoat(game, +1, 500, 2000));
        Thread goat2 = new Thread(newGoat(game, -1, 200, 500));

        goat1.start();
        goat2.start();

        goat1.join();
        goat2.join();

        System.out.println("winner: " + game.pos);
    }

    private static Thread newGoat(GoatGame game, int posChange, int min, int max) {
        return new Thread(() -> {
            while (!game.hasFallenGoat()) {
                doNothing(min, max);
                if (game.hasFallenGoat()) {
                    break;
                }
                game.push(posChange);
            }
        });
    }

    private static void doNothing(int min, int max) {
        var millis = nextInt(min, max);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int nextInt(int min, int max) {
        return min + ThreadLocalRandom.current().nextInt(max - min);
    }

    private boolean hasFallenGoat() {
        synchronized (GoatGame.class) {
            return pos < 0 || pos > boardLen;
        }
    }

    private void push(int posChange) {
        synchronized (GoatGame.class) {
            pos += posChange;
        }
        System.out.println("Push to " + pos + " by " + posChange);
    }
}
