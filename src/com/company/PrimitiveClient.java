package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static com.company.IOHelper.readFromFile;
import static com.company.IOHelper.readFromServer;
import static com.company.IOHelper.sendToServer;
import static com.company.IOHelper.writeToFile;
import static com.company.TimeLogger.logTime;

public class PrimitiveClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;
    private static final String INPUT_FILE_PATH = "resources/input.txt";
    private static final String OUTPUT_FILE_PATH = "resources/output.txt";

    public static void main(String[] args) {
        try (
                var socket = new Socket(HOST, PORT);
                var pwInput = new PrintWriter(socket.getOutputStream());
                var scInput = new Scanner(socket.getInputStream());

        ) {
            LocalDateTime startedAt = LocalDateTime.now();

            List<String> numsToSend = readFromFile(INPUT_FILE_PATH);
            sendToServer(numsToSend, pwInput);

            List<String> dataFromServer = readFromServer(scInput);
            writeToFile(dataFromServer, OUTPUT_FILE_PATH);

            // log elapsed time in millis
            logTime(startedAt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
