package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.Scanner;

public class PrimitiveClient {
    public static void main(String[] args) {
        final String HOST = "127.0.0.1";
        final int PORT = 12345;
        final BigDecimal SIZE = new BigDecimal(1000000);

        try (
                var s = new Socket(HOST, PORT);
                var sc = new Scanner(s.getInputStream());
                var pw = new PrintWriter(s.getOutputStream());
        ) {
            String msg = sc.nextLine();
            System.out.println(msg);

            msg = sc.nextLine();
            System.out.println(msg);
            msg = sc.nextLine();
            System.out.println(msg);


            pw.println(SIZE);
            pw.flush();

            while (sc.hasNextLine()) {
                String msg2 = sc.nextLine();
                System.out.println(msg2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
