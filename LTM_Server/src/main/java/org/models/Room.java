package org.models;

import java.sql.*;
import java.util.ArrayList;

import org.main.DB;

public class Room {
    private int id;
    private String name;
    private String password;
    private Account owner;

    public Room(int id, String name, String password, Account owner) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.owner = owner;
    }

    public static Room findRoom(int id) throws Exception {
        Connection conn = DB.conn();
        String sql = "SELECT * FROM room WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int rid = rs.getInt("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String username = rs.getString("owner");
            Account owner = Account.findAccount(username);
            conn.close();
            return new Room(rid, name, password, owner);
        }
        conn.close();
        throw new Exception("Room not found!");
    }

    public boolean checkPassword(String password) {
        return this.password.equals("") || this.password.equals(password);
    }

    public static Room create(String name, String password, Account owner) throws Exception {
        Connection conn = DB.conn();
        String sql = "INSERT INTO room (name, password, owner) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, password);
        ps.setString(3, owner.getUsername());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            Room room = new Room(rs.getInt(1), name, password, owner);
            conn.close();
            room.addMember(owner);
            return room;
        }
        conn.close();
        throw new Exception("Create room failed!");
    }

    public int getId() {
        return id;
    }

    public void addMember(Account account) {
        try {
            Connection conn = DB.conn();
            String sql = "INSERT INTO room_member (room_id, account_username, banned) VALUES (?, ?, 0)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, account.getUsername());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public boolean checkMember(Account account) throws Exception {
        try {
            Connection conn = DB.conn();
            String sql = "SELECT * FROM room_member WHERE room_id = ? AND account_username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, account.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getBoolean("banned")) {
                    conn.close();
                    throw new Exception("You are banned from this room!");
                }
                conn.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Message> getMessages(int start, int end) {
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            Connection conn = DB.conn();
            String sql = "SELECT * FROM message WHERE room = ? ORDER BY id DESC LIMIT ?, ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setInt(2, start);
            ps.setInt(3, end);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getInt("id"), Account.findAccount(rs.getString("sender")), rs.getString("message"), this));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean checkOwner(Account owner) {
        return this.owner.getUsername().equals(owner.getUsername());
    }

    public String getMembersAsString() {
        String members = "";
        try {
            Connection conn = DB.conn();
            String sql = "SELECT * FROM room_member WHERE room_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                members += rs.getString("account_username") + "/";
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }

    public void banMember(Account memberAccount) {
        try {
            Connection conn = DB.conn();
            String sql = "UPDATE room_member SET banned = 1 WHERE room_id = ? AND account_username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, memberAccount.getUsername());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void kickMember(Account memberAccount) {
        try {
            Connection conn = DB.conn();
            String sql = "DELETE FROM room_member WHERE room_id = ? AND account_username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, memberAccount.getUsername());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unbanMember(Account account) {
        try {
            Connection conn = DB.conn();
            String sql = "UPDATE room_member SET banned = 0 WHERE room_id = ? AND account_username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, account.getUsername());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeInfo(String name, String password) {
        password = Hash.hash(password);
        try {
            Connection conn = DB.conn();
            String sql = "UPDATE room SET name = ?, password = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setInt(3, this.id);
            ps.executeUpdate();
            conn.close();
            this.name = name;
            this.password = password;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
