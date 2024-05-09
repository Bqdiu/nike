package com.example.nike.Controller;

import android.os.AsyncTask;

import com.example.nike.Model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountHandler {
    private static DBConnection db = new DBConnection();

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
