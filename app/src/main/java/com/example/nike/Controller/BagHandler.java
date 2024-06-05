package com.example.nike.Controller;

import android.os.Build;

import com.example.nike.Model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BagHandler {
    private static DBConnection dbConnection = new DBConnection();
    public static boolean addToBag(int user_order_id, int product_size_id,int quantity) {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = dbConnection.connectionClass();

            String query = "INSERT INTO user_order_products (user_order_id, product_size_id,amount) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user_order_id);
            preparedStatement.setInt(2, product_size_id);
            preparedStatement.setInt(3,quantity);
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
}
