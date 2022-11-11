package org.main;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.models.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String room = null;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String cmd = this.receive();
                System.out.println("Received command: " + cmd);
                switch (cmd) {
                    case "login" -> this.login();
                    case "register" -> this.register();
                    case "createRoom" -> this.createRoom();
                    case "joinRoom" -> this.joinRoom();
                    case "getRoomList" -> this.getRoomList();
                    case "sendMessage" -> this.sendMessage();
                    case "setRoom" -> this.setRoom();
                    case "getMessages" -> this.getMessages();
                    case "getUsername" -> this.getUsername();
                    case "addMember" -> this.addMember();
                    case "getMembers" -> this.getMembers();
                    case "kickMember" -> this.kickMember();
                    case "banMember" -> this.banMember();
                    case "setRoomInfo" -> this.setRoomInfo();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                this.close();
                break;
            }
        }
    }

    private void addMember() {
        // token, room, username
        try {
            String token = this.receive();
            String ownerID = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account owner = Account.findAccount(ownerID);
            String roomID = this.receive();
            Room room = Room.findRoom(Integer.parseInt(roomID));
            // check if owner is in room
            if (room.checkOwner(owner)) {
                String username = this.receive();
                Account account = Account.findAccount(username);
                try {
                    if (room.checkMember(account)) {
                        this.send("Member already in room");
                        return;
                    }
                } catch (Exception e) {
                    room.unbanMember(account);
                    this.send("Member unbanned");
                    return;
                }
                room.addMember(account);
                this.send("ok");
                Message.create(owner, "added " + username + " to room!", room);
            } else {
                this.send("You are not owner of this room");
            }
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    private void getMembers() {
        // token, room
        try {
            String token = this.receive();
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            String roomID = this.receive();
            Room room = Room.findRoom(Integer.parseInt(roomID));
            // check if member is in room
            if (room.checkMember(account)) {
                this.send(room.getMembersAsString());
            } else {
                this.send("You are not member of this room");
            }
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    private void getUsername() {
        try {
            String token = this.receive();
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            this.send(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try {
            return dis.readUTF();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void login() {
        String username = this.receive();
        String password = this.receive();
        password = Hash.hash(password);
        try {
            Account.findAccount(username, password);
            String token = JWT.create().withClaim("username", username).sign(Algorithm.HMAC256("secret"));
            System.out.println("Token: " + token);
            this.send(token);
        } catch (Exception e) {
            this.send("null");
            this.send(e.getMessage());
        }
    }

    private void register() {
        String username = this.receive();
        String password = this.receive();
        password = Hash.hash(password);
        try {
            Account.create(username, password);
            String token = JWT.create().withClaim("username", username).sign(Algorithm.HMAC256("secret"));
            this.send(token);
        } catch (Exception e) {
            this.send("null");
            this.send(e.getMessage());
        }
    }

    private void createRoom() {
        String token = this.receive();
        String roomName = this.receive();
        String roomPassword = this.receive();
        roomPassword = Hash.hash(roomPassword);
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            Room room = Room.create(roomName, roomPassword, account);
            this.send(String.valueOf(room.getId()));
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    private void joinRoom() {
        String token = this.receive();
        String roomID = this.receive();
        String roomPassword = this.receive();
        roomPassword = Hash.hash(roomPassword);
        System.out.println("RoomID: " + roomID);
        System.out.println("RoomPassword: " + roomPassword);
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            Room room = Room.findRoom(Integer.parseInt(roomID));
            if (room.checkPassword(roomPassword) && !room.checkMember(account)) {
                room.addMember(account);
                Message.create(account, account.getUsername() + " joined the room!", room);
                this.send("ok");
            } else {
                this.send("Wrong password or you are already a member of this room!");
            }
        } catch (NumberFormatException e) {
            this.send("Room ID must be a number");
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    private void getRoomList() {
        String token = this.receive();
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            String response = "";
            for (Room room : account.getRoomList()) {
                response += room.getId() + "-" + room.getName() + "/";
            }
            this.send(response.substring(0, response.length() - 1));
        } catch (Exception e) {
            this.send("null");
        }
    }

    private void sendMessage() {
        String token = this.receive();
        String roomID = this.receive();
        String message = this.receive();
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            Room room = Room.findRoom(Integer.parseInt(roomID));
            if (room.checkMember(account)) {
                Message.create(account, message, room);
                System.out.println("Message sent to room " + roomID);
                this.send("ok");
            } else {
                this.send("You are not a member of this room");
            }
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    public String getRoom() {
        return room;
    }

    private void setRoom() {
        String token = this.receive();
        String room = this.receive();
        try {
            Room r = Room.findRoom(Integer.parseInt(room));
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            if (r.checkMember(account)) {
                this.room = room;
            }
        } catch (Exception e) {
            // pass
        }
    }

    private void getMessages() {
        String token = this.receive();
        String roomID = this.receive();
        int page = Integer.parseInt(this.receive());
        int size = Integer.parseInt(this.receive());
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            Room room = Room.findRoom(Integer.parseInt(roomID));
            if (!room.checkMember(account)) {
                this.send("0");
            }
            int start = page * size;
            int end = (page + 1) * size;
            ArrayList<Message> messages = room.getMessages(start, end);
            this.send(String.valueOf(messages.size()));
            for (int i = messages.size() - 1; i >= 0; i--) {
                Message message = messages.get(i);
                this.send(message.getSender().getUsername() + ": " + message.getMessage());
            }
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    private void banMember() {
        String token = this.receive();
        String roomID = this.receive();
        String member = this.receive();
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            Room room = Room.findRoom(Integer.parseInt(roomID));
            if (room.checkMember(account) && room.checkOwner(account)) {
                Account memberAccount = Account.findAccount(member);
                if (room.checkOwner(memberAccount)) {
                    throw new Exception("You can't ban yourself!");
                }
                room.banMember(memberAccount);
                Message.create(account, member + " was banned from the room!", room);
                this.send("ok");
            } else {
                this.send("You are not the owner of this room or you are not a member of this room");
            }
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    private void kickMember() {
        String token = this.receive();
        String roomID = this.receive();
        String member = this.receive();
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            Room room = Room.findRoom(Integer.parseInt(roomID));
            if (room.checkMember(account) && room.checkOwner(account)) {
                Account memberAccount = Account.findAccount(member);
                if (room.checkOwner(memberAccount)) {
                    throw new Exception("You can't kick yourself!");
                }
                room.kickMember(memberAccount);
                Message.create(account, member + " was kicked from the room!", room);
                this.send("ok");
            } else {
                this.send("You are not the owner of this room or you are not a member of this room");
            }
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }

    private void setRoomInfo() {
        String token = this.receive();
        String roomID = this.receive();
        String name = this.receive();
        String password = this.receive();
        try {
            String username = JWT.require(Algorithm.HMAC256("secret")).build().verify(token).getClaim("username").asString();
            Account account = Account.findAccount(username);
            Room room = Room.findRoom(Integer.parseInt(roomID));
            if (room.checkOwner(account)) {
                room.changeInfo(name, password);
                Message.create(account, "Room details changed!", room);
                this.send("ok");
            } else {
                this.send("You are not the owner of this room or you are not a member of this room");
            }
        } catch (Exception e) {
            this.send(e.getMessage());
        }
    }
}
