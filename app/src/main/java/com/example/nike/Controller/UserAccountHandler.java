package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountHandler {
    private static DBConnection db = new DBConnection();

    public static boolean checkUserExist(String email) throws SQLException {
        Connection con = db.connectionClass();
        int rs_count = 0;
        String sql = "select count(*) from user_account where user_email = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            rs_count = rs.getInt(1);
        }
        con.close();
        return rs_count > 0;
    }
    public static boolean addUserGoogle(String email, String fullname, String url) {
        Connection con = db.connectionClass();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO user_account (user_email, user_fullname, user_url) VALUES (?, ?, ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, fullname);
            preparedStatement.setString(3, url);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error adding user", e);
        }
    }


    public static boolean addUser(String username, String password, String gender, String email, String phoneNumber, String address, String firstName, String lastName, int memberTier, int point) {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = db.connectionClass();

            String query = "INSERT INTO user_account (user_username, user_password, user_gender, user_email, user_phone_number, user_address, user_first_name, user_last_name, user_member_tier, user_point) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, lastName);
            preparedStatement.setInt(9, memberTier);
            preparedStatement.setInt(10, point);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static void checkLogin(String email, String password) {

    }

}
