package com.company;

import java.io.File;
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

                    while (sc.hasNextLine()) {
                        var filename = sc.next();

                        var file = new File(filename);
                        if(file.exists()) {
                            try(var scFile = new Scanner(file)) {
                                while (scFile.hasNextLine()) {
                                    var line = scFile.nextLine();
                                    pw.println(line);
                                }
                            }
                        } else {
                            pw.println("Missing file " + filename);
                        }
                        pw.flush();

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
