package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

import static com.company.TimeLogger.logTime;

public class PrimitiveClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;
    private static final String INPUT_FILE_PATH = "resources/input.txt";
    private static final String OUTPUT_FILE_PATH = "resources/output.txt";

    public static void main(String[] args) {
        try (
                var socket = new Socket(HOST, PORT);
                var scFile = new Scanner(new File(INPUT_FILE_PATH));
                var scInput = new Scanner(socket.getInputStream());
                var pwInput = new PrintWriter(socket.getOutputStream());
                var fileWriter = new FileWriter(OUTPUT_FILE_PATH);
                var pwOutput = new PrintWriter(fileWriter);
        ) {
            LocalDateTime startedAt = LocalDateTime.now();
            String line;

            // sending data to server
            System.out.println("Sending");
            while (scFile.hasNextLine()) {
                line = scFile.nextLine();
                System.out.println(line);
                pwInput.println(line);
            }
            pwInput.flush();

            // response from server
            System.out.println("Receiving");
            while (scInput.hasNextLine()) {
                line = scInput.nextLine();
                int calculatedNumber = Integer.parseInt(line);

                System.out.println("Client Line");
                System.out.println(calculatedNumber);
                pwOutput.println(line);

                if (calculatedNumber == 0) {
                    System.out.println("Break");
                    break;
                }
            }
            // log elapsed time in millis
            logTime(startedAt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
