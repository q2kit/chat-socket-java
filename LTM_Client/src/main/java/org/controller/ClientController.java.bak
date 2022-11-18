package org.controller;

import org.views.ChatView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController {
    private Socket s = null;
    private Socket notificationSocket = null;
    private DataInputStream notificationIn = null;
    private DataOutputStream notificationOut = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String token = null;
    private String room = null;
    private Thread thread = null;
    private ChatView chatView = null;

    public ClientController() throws Exception {
        this.s = new Socket("localhost", 8002);
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
        this.notificationSocket = new Socket("localhost", 8001);
        this.notificationIn = new DataInputStream(notificationSocket.getInputStream());
        this.notificationOut = new DataOutputStream(notificationSocket.getOutputStream());
    }

    public ClientController(String token) throws Exception {
        this.token = token;
        this.s = new Socket("localhost", 8002);
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
        this.notificationSocket = new Socket("localhost", 8001);
        this.notificationIn = new DataInputStream(notificationSocket.getInputStream());
        this.notificationOut = new DataOutputStream(notificationSocket.getOutputStream());
    }

    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receive() {
        try {
            return dis.readUTF();
        } catch (Exception e) {
            return null;
        }
    }

    public String login(String username, String password) {
        this.send("login");
        this.send(username);
        this.send(password);
        String res = this.receive();
        if (res.equals("null")) {
            return "error" + "/" + this.receive(); // return error message
        }
        this.token = res;
        this.saveToken();
        return "success" + "/" + "Login successfully!"; // return success message
    }

    public String register(String username, String password) {
        this.send("register");
        this.send(username);
        this.send(password);
        String res = this.receive();
        if (res.equals("null")) {
            return "error" + "/" + this.receive(); // return error message
        }
        this.token = res;
        this.saveToken();
        return "success" + "/" + "Register successfully!"; // return success message
    }

    public void deleteToken() {
        try {
            String src = "C:\\Users\\" + System.getProperty("user.name") + "\\.chatapp\\token.txt";
            File file = new File(src);
            file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveToken() {
        String src = "C:\\Users\\" + System.getProperty("user.name") + "\\.chatapp\\token.txt";
        File file = new File(src);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(this.token);
            bw.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String createRoom(String roomName, String password) {
        this.send("createRoom");
        this.send(this.token);
        this.send(roomName);
        this.send(password);
        return this.receive();
    }

    public String joinRoom(String roomID, String password) {
        this.send("joinRoom");
        this.send(this.token);
        this.send(roomID);
        this.send(password);
        String res = this.receive();
        System.out.println("response:" + res + "end");
        return res;
    }

    public JList<String> getRoomList() {
        this.send("getRoomList");
        this.send(this.token);
        String res = this.receive();
        if (res.equals("null")) {
            return null;
        }
        System.out.println("getRoomList: " + res);
        String[] roomList = res.split("/");
        return new JList<>(roomList);
    }

    public void setRoom(String room) {
        this.send("setRoom");
        this.send(this.token);
        this.send(room); // split roomID from room, e.g. 1-abc -> 1
        this.room = room;
    }

    public String sendMessage(String message) {
        this.send("sendMessage");
        this.send(this.token);
        this.send(this.room);
        this.send(message); // save message to database
        try {
            this.notificationOut.writeUTF("newMessage:" + message); // send to other client
            this.notificationOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.receive();
    }

    public String getRoom() {
        return this.room;
    }

    public String getUsername() {
        this.send("getUsername");
        this.send(this.token);
        return this.receive();
    }

    public ArrayList<String> getMessages(int page, int pageSize) {
        System.out.println(page + " " + this.room);
        this.send("getMessages");
        this.send(this.token);
        this.send(this.room);
        this.send(String.valueOf(page));
        this.send(String.valueOf(pageSize));
        int n = Integer.parseInt(this.receive());
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            res.add(this.receive());
        }
        return res;
    }

    public void startThread() {
        try {
            this.thread = new Thread(() -> {
                while (true) {
                    try {
                        String msg = this.notificationIn.readUTF();
                        System.out.println(msg);
                        this.chatView.appendMessage("\n" + msg);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
//                        break;
                    }
                }
            });
            this.thread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setChatView(ChatView chatView) {
        try {
            this.notificationOut.writeUTF("setRoom:" + room + ":" + this.token);
            this.notificationOut.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.chatView = chatView;
    }

    public String addMember(String username) {
        this.send("addMember");
        this.send(this.token);
        this.send(this.room);
        this.send(username);
        return this.receive();
    }

    public ChatView getChatView() {
        return this.chatView;
    }

    public ArrayList<String> getMembers() {
        this.send("getMembers");
        this.send(this.token);
        this.send(this.room);
        ArrayList<String> res = new ArrayList<>();
        for (String username : this.receive().split("/")) {
            res.add(username);
        }
        return res;
    }

    public String banMember(String member) {
        this.send("banMember");
        this.send(this.token);
        this.send(this.room);
        this.send(member);
        return this.receive();
    }

    public String kickMember(String member) {
        this.send("kickMember");
        this.send(this.token);
        this.send(this.room);
        this.send(member);
        return this.receive();
    }

    public String setRoomInfo(String name, String password) {
        this.send("setRoomInfo");
        this.send(this.token);
        this.send(this.room);
        this.send(name);
        this.send(password);
        return this.receive();
    }

    public String uploadFile(File file) {
        return FileUploader.uploadFile("https://q2k.dev/api/up/", file);
    }
}