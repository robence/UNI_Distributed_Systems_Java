package com.chatparallel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParallelServer {
    public static void main(String[] args) {
        String serverPort = args[0];
        final int SERVER_PORT = Integer.parseInt(serverPort);
        startServer(SERVER_PORT);
    }

    private static void startServer(final int SERVER_PORT) {
        List<Socket> friends = new ArrayList<>();
        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT)
        ) {
            while (true) {
                var friend = ss.accept();
                startFriend(friends, friend).start();
                friends.add(friend);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Thread startFriend(List<Socket> friends, Socket self) {
        return new Thread(() -> {
//                    PrintWriter selfPrintWriter = new PrintWriter(self.getOutputStream())
            sendToFriends(friends, self);
            receiveFromFriends(friends, self);
        });
    }

    private static void sendToFriends(List<Socket> friends, Socket self) {
        try {
            sendMessagesToFriends(friends, new Scanner(self.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessagesToFriends(List<Socket> friends, Scanner sc) {
        while (sc.hasNextLine()) {
            friends.stream().forEach(((var friend) -> {
                try {
                    PrintWriter friendPrintWriter = new PrintWriter(friend.getOutputStream());
                    sendToFriend(sc, friendPrintWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
    }

    private static Integer sendToFriend(Scanner sc, PrintWriter pw) {
        while (sc.hasNextLine()) {
            var line = sc.nextLine();
            pw.println(line);
            pw.flush();
        }
        return 0;
    }

    private static void receiveFromFriends(List<Socket> friends, Socket self) {

    }
}
