package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class IOHelper {
    static void sendToServer(List<String> payload, PrintWriter pwInput) {

        for (String line : payload) {
            pwInput.println(line);
        }
        pwInput.flush();
    }

    static List<String> readFromServer(Scanner scInput) {
        List<String> lines = new ArrayList<>();
        String line;
        // response from server
        System.out.println("Receiving");
        while (scInput.hasNextLine()) {
            line = scInput.nextLine();
            lines.add(line);

            if (line.equals("0")) {
                break;
            }
        }
        System.out.println("Lines: " + lines.size());
        return lines;

    }

    static List<String> readFromFile(String filename) {
        List<String> nums = new ArrayList<>();
        String line;

        try (
                var scFile = new Scanner(new File(filename))
        ) {
            while (scFile.hasNextLine()) {
                line = scFile.nextLine();
                nums.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nums;
    }

    static void writeToFile(List<String> lines, String filename) {
        try (
                var fileWriter = new FileWriter(filename);
                var pwOutput = new PrintWriter(fileWriter)
        ) {
            for (String line : lines) {
                pwOutput.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
