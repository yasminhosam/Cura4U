package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // 1. DATABASE CREDENTIALS (CHANGE THESE!)
    private static final String URL = "jdbc:mysql://localhost:3306/clinic_system"; // <--- Put your exact DB name here
    private static final String USER = "root";
    private static final String PASSWORD = ""; // <--- Put YOUR local password here

    public static Connection connect() {
        Connection conn = null;
        try {
            // 2. Load the Driver (Older Java versions need this)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. Open Connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connection Successful!");

        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: MySQL Driver not found. Did you add the JAR?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error: Connection Failed. Check your URL, model.User, or Password.");
            e.printStackTrace();
        }
        return conn;
    }

    // ui.Main method just to test it right now
    public static void main(String[] args) {
        connect();
    }
}