package dao;

import database.DBConnection;
import model.Doctor;
import model.Patient;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // This method handles the saving
    public static boolean registerUser(User user) {
        Connection conn = DBConnection.connect();

        if (conn == null) return false;

        String sql = "INSERT INTO users (name, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, String.valueOf(user.getPhone()));
            pstmt.setString(5, user.getRole());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User checkLogin(String username, String password) {
        Connection conn = DBConnection.connect();
        if (conn == null) return null;

        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");


                User user;
                if ("doctor".equalsIgnoreCase(role)) {
                    user = new Doctor(name, email, phone, password);
                } else {
                    user = new Patient(name, email, phone, password);
                }

                user.setId(id);

                return user; // Return the full object
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
}