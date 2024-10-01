package br.app.com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/salao";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static Connection getConnectionWithoutDatabase() throws SQLException {
        String urlWithoutDatabase = "jdbc:postgresql://localhost:5432/"; // URL sem o banco de dados
        return DriverManager.getConnection(urlWithoutDatabase, USER, PASSWORD);
    }
}
