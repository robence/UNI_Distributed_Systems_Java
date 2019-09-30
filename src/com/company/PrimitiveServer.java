package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class PrimitiveServer {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {

        int clientCount = 0;

        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT);
        ) {
            while (true) {
                try (Socket s = ss.accept();
                     Scanner sc = new Scanner(s.getInputStream());
                     PrintWriter pw = new PrintWriter(s.getOutputStream());
                ) {
                    ++clientCount;
                    pw.println(clientCount);
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
