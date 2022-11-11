package org.main;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.models.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class NotificationHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String room = null;
    private String token = null;
    private String username;

    public NotificationHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = this.dis.readUTF();
                if (message.indexOf("newMessage") == 0) {
                    this.sendToRoom(this.username + ": " + message.substring(11));
                } else if (message.indexOf("setRoom") == 0) {
                    message = message.substring(8);
                    this.room = message.split(":")[0];
                    this.token = message.split(":")[1];
                    this.username = JWT.require(Algorithm.HMAC256("secret")).build().verify(this.token).getClaim("username").asString();
                    Account account = Account.findAccount(this.username);
                    Room room = Room.findRoom(Integer.parseInt(this.room));
                    if (!room.checkMember(account)) {
                        this.close();
                        break;
                    }
                    this.sendToRoom(this.username + " jumped to room " + this.room);
                }
            }
        } catch (Exception e) {
            System.out.println("socket closed" + e.getMessage());
            this.close();
        }
    }

    public void close() {
        try {
            this.dis.close();
            this.dos.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendToRoom(String message) {
        try {
            for (NotificationHandler nh : Server.clients) {
                if (nh.room.equals(this.room)) {
                    nh.dos.writeUTF(message);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
