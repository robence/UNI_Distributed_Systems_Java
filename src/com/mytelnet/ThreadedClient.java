package com.mytelnet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedClient {
    public static void main(String[] args) {
        var HOST = args[0];
        var PORT = Integer.parseInt(args[1]);
        try (
                var s = new Socket(HOST, PORT);
                var scSocket = new Scanner(s.getInputStream());
                var pw = new PrintWriter(s.getOutputStream());
                var scIn = new Scanner(System.in)
        ) {


            var t1 = new Thread(() -> {
                while (scIn.hasNextLine()) {
                    var line = scIn.nextLine();
                    pw.println(line);
                    pw.flush();
                }
            });

            var t2 = new Thread(() -> {
                while (scSocket.hasNextLine()) {
                    var line = scSocket.nextLine();
                    System.out.println(line);
                }
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
