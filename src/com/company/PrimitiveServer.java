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

            String line;
            System.out.println("Receiving");
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                int number = Integer.parseInt(line);
                System.out.println("Received: " + number);
                int calculatedNumber = calc(number);
                System.out.println("Sending: " + calculatedNumber);
                pw.println(calculatedNumber);
            }

            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int calc(int number) {
        if (number == 0) { return 0; }
        return number * 2 + 1;
    }
}
