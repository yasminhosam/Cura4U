package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {

    public static boolean saveClinicInfo(String username, String address, String department, String price, String description) {
        Connection conn = DBConnection.connect();
        if (conn == null) return false;

        try {
            int userId=-1;
            String getIdSql="SELECT user_id FROM users WHERE name =?";
            PreparedStatement getIdStmt=conn.prepareStatement(getIdSql);
            getIdStmt.setString(1,username);
            ResultSet rs=getIdStmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id"); // Get the number (e.g., 5)
            } else {
                System.out.println("Error: model.User not found with name: " + username);
                return false;
            }
            String sql = "INSERT INTO doctor (user_id, department, price, address, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, department);
            pstmt.setString(3, price);
            pstmt.setString(4, address);
            pstmt.setString(5, description);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}