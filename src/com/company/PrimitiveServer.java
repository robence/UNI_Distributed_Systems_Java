package com.company;

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
                Socket s = ss.accept();
                Scanner sc = new Scanner(s.getInputStream());
                PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            System.out.println("Listening on port: " + SERVER_PORT);
            pw.flush();
            int length = Integer.parseInt(sc.nextLine());

            String line;
            System.out.println("Receiving");
            for (int i = 0; i < length; i ++) {
                line = sc.nextLine();
                int number = Integer.parseInt(line);
                System.out.println("Received: " + number);
                int calculatedNumber = calc(number);
                String data = String.valueOf(calculatedNumber);
                System.out.println("Sending: " + data);
                System.out.println("---");
                pw.println(data);
            }
            pw.flush();

            System.out.println("Flushed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int calc(int number) {
        if (number == 0) {
            return 0;
        }
        return number * 2 + 1;
    }
}
