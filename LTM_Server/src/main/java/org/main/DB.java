package org.main;

import java.sql.*;

public class DB {
    public static Connection conn() throws SQLException, ClassNotFoundException {
        String hostName = "q2k.dev";
        String dbName = "ltm";
        String userName = "ltm";
        String password = "Abcd123@";
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?zeroDateTimeBehavior=convertToNull";
        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
