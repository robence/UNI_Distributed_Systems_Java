package com.company;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class PrimitiveClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;
    private static final String FILE_PATH = "resources/input.txt";

    public static void main(String[] args) {

        try (
                var s = new Socket(HOST, PORT);
                var scFile = new Scanner(new File(FILE_PATH));
                var scInput = new Scanner(s.getInputStream());
                var pw = new PrintWriter(s.getOutputStream());
        ) {
            LocalDateTime start = LocalDateTime.now();
            String line;
            // sending data to server
            System.out.println("Sending");
            while (scFile.hasNextLine()) {
                line = scFile.nextLine();
                int numberToSend = Integer.parseInt(line);
                System.out.println(numberToSend);
                pw.println(numberToSend);
            }
            pw.flush();


            // response from server
            System.out.println("Receiving");
            while (scInput.hasNextLine()) {
                line = scInput.nextLine();
                int calculatedNumber = Integer.parseInt(line);

                System.out.println(calculatedNumber);
                // TODO: write to file

                if (calculatedNumber == 0) {
                    break;
                }
            }

            // log elapsed time in millis
            logTime(start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logTime(LocalDateTime start) {
        LocalDateTime now = LocalDateTime.now();
        long millis = ChronoUnit.MILLIS.between(start, now);

        System.out.println("Start: " + start);
        System.out.println("Now " + now);
        System.out.println("Time: " + millis + " millis");
    }
}
