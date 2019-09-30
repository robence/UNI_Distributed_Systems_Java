package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class HelperServer {
    private static final String INPUT_FILE_PATH = "resources/numbers.txt";

    public static void main(String[] args) {
        String serverPort = args[0];
        final int SERVER_PORT = Integer.parseInt(serverPort);
        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT);
                Socket s = ss.accept();
                Scanner sc = new Scanner(s.getInputStream());
                PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
            List<String> magicNumbers = IOHelper.readFromFile(INPUT_FILE_PATH);
            System.out.println("Sending " + magicNumbers.size() + " numbers(s)");
            for (String port : magicNumbers) {
                pw.println(port);
            }
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
