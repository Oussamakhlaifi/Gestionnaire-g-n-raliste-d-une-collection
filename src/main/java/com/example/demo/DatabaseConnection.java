package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/collectiondb"; // Nom de la base
    private static final String USER = "root"; // Utilisateur
    private static final String PASSWORD = ""; // Mot de passe vide

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}