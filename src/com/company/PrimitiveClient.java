package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static com.company.IOHelper.readFromServer;
import static com.company.TimeLogger.logTime;

public class PrimitiveClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;
    private static final String OUTPUT_FILE_PATH = "resources/sum.txt";

    public static void main(String[] args) {
        try (
                var socket = new Socket(HOST, PORT);
                var pwInput = new PrintWriter(socket.getOutputStream());
                var scInput = new Scanner(socket.getInputStream());
                var fileWriter = new FileWriter(OUTPUT_FILE_PATH);
                var pwOutput = new PrintWriter(fileWriter)) {
            LocalDateTime startedAt = LocalDateTime.now();
            int sumOfAllReceivedNumbers = 0;

            List<String> portsFromServer = readFromServer(scInput);
            for (String port : portsFromServer) {
                final int PORT_FROM_SERVER = Integer.parseInt(port);

                try (
                        var dynamicSocket = new Socket(HOST, PORT_FROM_SERVER);
                        var sc = new Scanner(dynamicSocket.getInputStream());

                ) {
                    while (sc.hasNextInt()) {
                        int currentNum = sc.nextInt();
                        sumOfAllReceivedNumbers += currentNum;
                        pwOutput.println(sumOfAllReceivedNumbers);
                    }
                }

            }
            // log elapsed time in millis
            logTime(startedAt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
