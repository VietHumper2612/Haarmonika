package com.example.haarmonika.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String PROPERTIES_FILE = "src/main/resources/db.properties";
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private static DatabaseConnection instance;
    private Connection connection;

    // Load database credentials from properties file
    static {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
        }
    }

    // Private constructor to enforce singleton pattern
    private DatabaseConnection() {
        connect();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Establish a new database connection
    private void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
        }
    }

    // Ensure we always return a valid connection
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Reconnecting to the database...");
                connect();
            }
        } catch (SQLException e) {
            System.err.println("Error checking database connection: " + e.getMessage());
        }
        return connection;
    }
}
