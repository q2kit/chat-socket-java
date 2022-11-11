package org.models;

import java.sql.*;

import org.main.DB;


public class Message {
    private int id;
    private Account sender;
    private String message;
    private Room room;

    public Message(int id, Account sender, String message, Room room) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.room = room;
    }

    public static Message create(Account sender, String message, Room room) throws Exception {
        Connection conn = DB.conn();
        String sql = "INSERT INTO message (sender, message, room) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, sender.getUsername());
        ps.setString(2, message);
        ps.setInt(3, room.getId());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            conn.close();
            return new Message(id , sender, message, room);
        }
        conn.close();
        throw new Exception("Create message failed!");
    }

    public Account getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
