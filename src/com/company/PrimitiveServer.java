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
        ) {

            while (true) {
                try (
                        Socket s1 = ss.accept();
                        Scanner sc1 = new Scanner(s1.getInputStream());
                        PrintWriter pw1 = new PrintWriter(s1.getOutputStream());

                        Socket s2 = ss.accept();
                        Scanner sc2 = new Scanner(s2.getInputStream());
                        PrintWriter pw2 = new PrintWriter(s2.getOutputStream());
                ) {

                    while (sc1.hasNextLine()) {
                        String line = sc1.nextLine();

                        pw2.println(line);
                        pw2.flush();

                        if (!sc2.hasNextLine()) break;
                            String line2 = sc2.nextLine();
                            pw1.println(line2);
                            pw2.flush();

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
