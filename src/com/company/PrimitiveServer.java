package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class PrimitiveServer {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {

        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT);
        ) {

            while (true) {
                try (
                        ClientData client1 = new ClientData(ss);
                        ClientData client2 = new ClientData(ss);
                ) {
                    while (true) {
                        if (!client1.sendMsg(client2)) break;
                        if (!client2.sendMsg(client1)) break;
                    }
                } finally {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientData implements AutoCloseable {
    private Socket s;
    private Scanner sc;
    private PrintWriter pw;

    private String name;
    private int msgId = 0;

    ClientData(ServerSocket ss) throws IOException {
        s = ss.accept();
        sc = new Scanner(s.getInputStream());
        pw = new PrintWriter(s.getOutputStream());

        name = sc.nextLine();
    }

    boolean sendMsg(ClientData other) {
        if (!sc.hasNextLine()) return false;
        String line = sc.nextLine();
        ++msgId;

        other.getPw().printf("%s #%d: %s%n", getName(), getMsgId(), line);
        other.getPw().flush();
        return true;
    }

    public Scanner getSc() {
        return this.sc;
    }

    private PrintWriter getPw() {
        return this.pw;
    }

    public String getName() {
        return name;
    }

    public int getMsgId() {
        return msgId;
    }

    @Override
    public void close() throws Exception {
        if (s != null) {
            s.close();
        }
    }
}
