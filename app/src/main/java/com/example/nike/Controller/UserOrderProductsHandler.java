package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserOrderProductsHandler {
    private static DBConnection dbConnection = new DBConnection();
    public static boolean insertOrder(int user_order_id,int product_size_id,int amount)
    {
        Connection conn = dbConnection.connectionClass();
        String sql = "insert into user_order_products (user_order_id,product_size_id,amount) values (?,?,?)";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,user_order_id);
            preparedStatement.setInt(2,product_size_id);
            preparedStatement.setInt(3,amount);
            int rowInserted = preparedStatement.executeUpdate();
            return rowInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
