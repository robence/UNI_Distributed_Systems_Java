package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.IntStream;

public class PrimitiveServer {
    private static final int SERVER_PORT = 12345;
    private static final String TO_REPEAT = "Param";

    public static void main(String[] args) {

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
                pw.println(TO_REPEAT);
            });
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
