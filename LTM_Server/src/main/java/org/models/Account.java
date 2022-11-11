package org.models;

import org.main.DB;

import java.sql.*;
import java.util.ArrayList;

public class Account {
    private String username;
    private String password;

    public Account() {
    }


    public String getUsername() {
        return username;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Account findAccount(String username, String password) throws Exception {
        Connection conn = DB.conn();
        String sql = "SELECT * FROM account WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        Account account = null;
        if (rs.next()) {
            account = new Account(rs.getString("username"), rs.getString("password"));
            conn.close();
            if (!account.checkPassword(password)) {
                throw new Exception("Wrong password!");
            } else {
                return account;
            }
        }
        conn.close();
        throw new Exception("Account not found!");
    }

    public static Account findAccount(String username) throws Exception {
        Connection conn = DB.conn();
        String sql = "SELECT * FROM account WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Account account = new Account(rs.getString("username"), rs.getString("password"));
            conn.close();
            return account;
        }
        conn.close();
        throw new Exception("Account not found!");
    }

    public static Account create(String username, String password) throws Exception {
        if (checkExist(username)) {
            throw new Exception("Username already exists!");
        }
        Connection conn = DB.conn();
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();
        conn.close();
        return new Account(username, password);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public static boolean checkExist(String username) throws Exception {
        Connection conn = DB.conn();
        String sql = "SELECT * FROM account WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        conn.close();
        return result;
    }

    public Iterable<? extends Room> getRoomList() {
        try {
            Connection conn = DB.conn();
            String sql = "SELECT room.* FROM room, room_member WHERE room_member.account_username = ? AND room_member.room_id = room.id AND room_member.banned = 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, this.username);
            ResultSet rs = ps.executeQuery();
            ArrayList<Room> roomList = new ArrayList<>();
            while (rs.next()) {
                Room room = new Room(rs.getInt("id"), rs.getString("name"), rs.getString("password"), Account.findAccount(rs.getString("owner")));
                roomList.add(room);
            }
            conn.close();
            return roomList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
