package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserFavoriteProductsHanlder {
    private static DBConnection db = new DBConnection();

    public static boolean insertFavoriteProduct(int user_id, int product_id)
    {
        Connection conn = db.connectionClass();
        String sql = "insert into user_favorite_products (user_id,product_id) values (?,?)";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,product_id);
            int rowInserted = preparedStatement.executeUpdate();
            return rowInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
