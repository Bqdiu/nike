package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.UserFavoriteProducts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FavoriteProductHandler {
    private static DBConnection dbConnection = new DBConnection();

    public static boolean insertFavoriteProduct(int user_id, int product_id)
    {
        Connection conn = dbConnection.connectionClass();
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

    public static ArrayList<UserFavoriteProducts> getData(int UserID){
        Connection conn = dbConnection.connectionClass();
        ArrayList<UserFavoriteProducts> list = new ArrayList<>();
        if(conn!=null){
            String sql = "Select * from user_favorite_products where user_id ="+UserID;
            try
            {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                while (rs.next()){
                    UserFavoriteProducts userFavoriteProducts = new UserFavoriteProducts();
                    userFavoriteProducts.setId(rs.getInt(1));
                    userFavoriteProducts.setUser_id(rs.getInt(2));
                    userFavoriteProducts.setProduct_id(rs.getInt(3));
                    list.add(userFavoriteProducts);
                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static boolean CheckProductFavorite(int userID,int productID)  {
        Connection conn = dbConnection.connectionClass();

        if(conn!=null){
            String sql = "SELECT COUNT(*) FROM user_favorite_products where user_id = ? and product_id = ?";
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;


            try{
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1,userID);
                preparedStatement.setInt(2,productID);
               rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return false;
    }
    public static boolean removeFavoriteProduct(int user_id, int product_id)
    {
        Connection conn = dbConnection.connectionClass();
        String sql = "delete from user_favorite_products where user_id = ? and product_id = ?";
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
