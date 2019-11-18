package com.chatparallel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedChatServer {
    public static void main(String[] args) {
        try (
                ServerSocket ss = new ServerSocket(12345);
                var client1 = new ClientData(ss);
                var client2 = new ClientData(ss)
        ) {

            var t1 = new Thread(client1);
            var t2 = new Thread(client2);

            client1.setOther(client2);
            client2.setOther(client1);

            t1.start();
            t2.start();

            t1.join();
            t2.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Thread lineCopierThread(Scanner sc, PrintWriter pw) {
        return new Thread(() -> {

        });
    }

}

class ClientData implements AutoCloseable, Runnable {
    Socket s;
    Scanner sc;
    PrintWriter pw;
    ClientData other;
    String name;

    ClientData(ServerSocket ss) {
        try {
            s = ss.accept();
            sc = new Scanner(s.getInputStream());
            pw = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        s.close();
    }

    public Socket getS() {
        return s;
    }

    public Scanner getSc() {
        return sc;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setOther(ClientData other) {
        this.other = other;
    }

    @Override
    public void run() {
        var name = sc.nextLine();
        while (sc.hasNextLine()) {
            var line = sc.nextLine();
           other.pw.printf("%s to %s: %s%n", name, other.name, line);
           other.pw.flush();
        }
    }
}
