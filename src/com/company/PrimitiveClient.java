package com.company;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PrimitiveClient {
    private static final int CLIENT_PORT = 12345;

    public static void main(String[] args) {

        try (
                var s = new Socket("localhost", CLIENT_PORT);
                var sc = new Scanner(s.getInputStream());
                var pw = new PrintWriter(s.getOutputStream());

                var scIn = new Scanner(System.in);
                var pwOut = new PrintWriter(System.out);
        ) {

            sendMsg(scIn, pw);

            if(args.length > 0) {
                sendMsg(sc, pwOut);
            }

            while (true) {
                if (!sendMsg(scIn, pw)) break;
                if (!sendMsg(sc, pwOut)) break;
            }
        } catch (Exception e) {
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

