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

                    while (true) {
                        if (!sendMsg(sc1, pw2)) break;
                        if (!sendMsg(sc2, pw1)) break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean sendMsg(Scanner sc, PrintWriter pw) {
        if (!sc.hasNextLine()) return false;
        String line = sc.nextLine();
        pw.println(line);
        pw.flush();
        return true;
    }
}
