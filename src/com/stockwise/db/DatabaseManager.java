package com.stockwise.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:inventory.db";
    private static Connection connection = null;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Product table
            stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "price REAL NOT NULL)");

            // Supplier table
            stmt.execute("CREATE TABLE IF NOT EXISTS suppliers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "contact TEXT)");

            // Purchase Order table
            stmt.execute("CREATE TABLE IF NOT EXISTS purchase_orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "product_id INTEGER," +
                    "supplier_id INTEGER," +
                    "quantity INTEGER," +
                    "date TEXT," +
                    "FOREIGN KEY(product_id) REFERENCES products(id)," +
                    "FOREIGN KEY(supplier_id) REFERENCES suppliers(id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public static void executeUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }
}
