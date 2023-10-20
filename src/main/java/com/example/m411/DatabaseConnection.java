package com.example.m411;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Diese Methode stellt die Verbindung zur Datenbank her und gibt sie zurück
    public static Connection getDatabase() {
        String url = "jdbc:mysql://localhost:3306/m411db";
        String user = "root";
        String password = "";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
