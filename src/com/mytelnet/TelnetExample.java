package com.mytelnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class TelnetExample {
    public static void main(String[] args) {
        Scanner scIn = new Scanner(new InputStreamReader(System.in));

        System.out.println("Port:");
        String host = "localhost";
        int httpPort = scIn.nextInt();

        try (
                Socket s = new Socket(host, httpPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
//                Scanner sc = new Scanner(s.getInputStream());
                PrintWriter pw = new PrintWriter(s.getOutputStream())
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
