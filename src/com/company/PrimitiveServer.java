package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class PrimitiveServer {
    private static final int SERVER_PORT = 12345;
    private static final String INPUT_FILE_PATH = "resources/ports.txt";

    public static void main(String[] args) {

        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT);
        ) {

            // TODO: should start servers here

            while (true) {
                try (Socket s = ss.accept();
                     Scanner sc = new Scanner(s.getInputStream());
                     PrintWriter pw = new PrintWriter(s.getOutputStream());
                ) {

                    List<String> portNumbers = IOHelper.readFromFile(INPUT_FILE_PATH);
                    System.out.println("Sending " + portNumbers.size() + " port(s)");
                    for (String port : portNumbers) {
                        pw.println(port);
                    }
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
