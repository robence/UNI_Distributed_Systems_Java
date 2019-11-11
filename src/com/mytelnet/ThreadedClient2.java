package com.mytelnet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedClient2 {
    public static void main(String[] args) {
        var HOST = args[0];
        var PORT = Integer.parseInt(args[1]);
        try (
                var s = new Socket(HOST, PORT);
                var scSocket = new Scanner(s.getInputStream());
                var pw = new PrintWriter(s.getOutputStream());
                var scIn = new Scanner(System.in)
        ) {

            var t1 = lineCopierThread(scIn, pw);
            var t2 = lineCopierThread(scSocket, new PrintWriter(System.out));

            t1.start();
            t2.start();

            t1.join();
            t2.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread lineCopierThread(Scanner sc, PrintWriter pw) {
        return new Thread(() -> {
            while (sc.hasNextLine()) {
                var line = sc.nextLine();
                pw.println(line);
                pw.flush();
            }
        });
    }

}
