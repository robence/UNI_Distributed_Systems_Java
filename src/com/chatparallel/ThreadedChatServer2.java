package com.chatparallel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ThreadedChatServer2 {
    public static void main(String[] args) {

//        Set<ClientData2> clients = new ConcurrentHashMap<>();
        Set<ClientData2> clients = new HashSet<>();

        try (
                ServerSocket ss = new ServerSocket(12345)
        ) {

            while (true) {
                var client = new ClientData2(ss, clients);
                new Thread(client).start();
                synchronized (clients) {
                    clients.add(client);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Thread lineCopierThread(Scanner sc, PrintWriter pw) {
        return new Thread(() -> {

        });
    }

}

class ClientData2 implements AutoCloseable, Runnable {
    Socket s;
    Scanner sc;
    PrintWriter pw;
    String name;
    Set<ClientData2> clients;

    ClientData2(ServerSocket ss, Set<ClientData2> clients) {
        try {
            s = ss.accept();
            sc = new Scanner(s.getInputStream());
            pw = new PrintWriter(s.getOutputStream());
            this.clients = clients;
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

    @Override
    public void run() {
        var name = sc.nextLine();
        while (sc.hasNextLine()) {
            var line = sc.nextLine();

            Set<ClientData2> copy;
            synchronized (clients) {
                copy = new HashSet<>(clients);
            }

            for (var other : copy) {
                if (other == this) continue;

                synchronized (clients) {
                    if (!clients.contains(other)) continue;
                    other.pw.printf("%s to %s: %s%n", name, other.name, line);
                    other.pw.flush();
                }
            }
        }

        synchronized (clients) {
            clients.remove(this);
        }
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
