package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.IntStream;

public class PrimitiveServer {
    public static void main(String[] args) {
        final int SERVER_PORT = 12345;

        final String text = "Param";
        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT);
                Socket s = ss.accept();
                Scanner sc = new Scanner(s.getInputStream());
                PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            pw.println("Hello Client");
            pw.println("");
            pw.println("Enter a number:");
            pw.flush();

            int num = sc.nextInt();

            IntStream.range(0, num).forEach(i -> {
                pw.println(text);
            });
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
