package com.byultudy.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
    private final String username = "root";
    private final String password = "root";
    private final String url = "jdbc:mariadb://localhost/tobi";
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(
                url, username, password
        );
    }
}
