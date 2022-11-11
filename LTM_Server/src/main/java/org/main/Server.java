package org.main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 8002;
    public static ArrayList<NotificationHandler> clients = new ArrayList<NotificationHandler>();

    private ServerSocket ss;
    private ServerSocket notificationServerSocket;
    private int notificationPort = 8001;

    public Server() throws Exception {
        new Thread(() -> {
            try {
                notiSocket();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();
        this.ss = new ServerSocket(PORT);
        System.out.println("Command server started on port " + PORT);
        while (true) {
            Socket s = ss.accept();
            ClientHandler ch = new ClientHandler(s);
            new Thread(ch).start();
            System.out.println("New client(" + s.getInetAddress().getHostAddress() + ") connected to command server");
        }
    }

    public void notiSocket() throws Exception {
        System.out.println("Notification server started on port " + notificationPort);
        this.notificationServerSocket = new ServerSocket(notificationPort);
        while (true) {
            Socket s = notificationServerSocket.accept();
            NotificationHandler nh = new NotificationHandler(s);
            clients.add(nh);
            new Thread(nh).start();
            System.out.println("New client(" + s.getInetAddress().getHostAddress() + ") connected to notification server");
        }
    }
}
