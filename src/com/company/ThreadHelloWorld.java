package com.company;

import java.util.function.Function;

public class ThreadHelloWorld {
    public static void main(String[] args) {
        Runnable r = () -> {
            for (int i = 0; i < 3000; ++i) {
                System.out.println("Hello world " + i);
            }
        };

        Function<String, Runnable> f = (str) -> () -> {
            for (int i = 0; i < 3000; ++i) {
                System.out.println(str + " " + i);
            }
        };

//        r.run();

//        new Thread(r).start();

//        new Thread(f.apply("a")).start();


        Object lock = new Object();
        int[] counter = {0};
        String txt = "abc";

//        Thread y = new Thread(szovegesSzal("b", 3000));
//        Thread x = new Thread(szovegesSzal("a", 3000));

//        Thread x = new Thread(szovegesSzal("a", 3000));
//        Thread y = new Thread(szovegesSzal("b", 3000));

        Thread x = new Thread(counterRunnable(counter));
        Thread y = new Thread(counterRunnable(counter));

        x.start();
        y.start();

        try {
            x.join();
            y.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All done");
        System.out.println(counter[0]);

    }

    private static Runnable szovegesSzal(String id, int lepesSzam) {
        return () -> {
            for (int i = 0; i < lepesSzam; ++i) {
                System.out.println(id + " " + i);
            }
        };
    }


    private static Runnable counterRunnable(int[] counter) {
        return () -> {
            for (int i = 0; i < 100_000; ++i) {
                synchronized (counter) {
                    ++counter[0];
                }
            }
        };
    }
}


class MyThread extends Thread {

//    public MyThread(boolean someCondition) {
//        flag = someCondition;
//    }

    //    @Override
    public void run(String s) {
        System.out.println("MyThread");
    }


}

class MyRunnable implements Runnable {
    public void run() {
        System.out.println("MyRunnable");
    }
}
