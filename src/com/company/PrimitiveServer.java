package com.company;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class PrimitiveServer {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {

        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT);
        ) {

            var clientSum = 0;

            while (true) {
                try (Socket s = ss.accept();
                     Scanner sc = new Scanner(s.getInputStream());
                     PrintWriter pw = new PrintWriter(s.getOutputStream());
                ) {

                    while (sc.hasNextInt()) {
                        var num = sc.nextInt();
                        clientSum += num;

                        pw.println(clientSum);
                        pw.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
