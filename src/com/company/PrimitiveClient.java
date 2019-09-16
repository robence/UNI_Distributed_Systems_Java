package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class PrimitiveClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;
    private static final BigDecimal SIZE = new BigDecimal(1000000);

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();

        try (
            var s = new Socket(HOST, PORT);
            var sc = new Scanner(s.getInputStream());
            var pw = new PrintWriter(s.getOutputStream());
        ) {
            String welcome = sc.nextLine();
            System.out.println(welcome);

            String emptyLine = sc.nextLine();
            System.out.println(emptyLine);

            String prompt = sc.nextLine();
            System.out.println(prompt);

            pw.println(SIZE);
            pw.flush();

            while (sc.hasNextLine()) {
                String msg2 = sc.nextLine();
                System.out.println(msg2);
            }

            LocalDateTime now = LocalDateTime.now();
            long millis = ChronoUnit.MILLIS.between(start, now);

            System.out.println("Size: " + SIZE);
            System.out.println("Start: " + start);
            System.out.println("Now " + now);
            System.out.println("Time: " + millis + " millis");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
