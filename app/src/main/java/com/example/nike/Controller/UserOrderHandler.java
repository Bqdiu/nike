package com.example.nike.Controller;

import android.os.Build;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.UserFavoriteProducts;
import com.example.nike.Model.UserOrder;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserOrderHandler {
    private static DBConnection dbConnection = new DBConnection();

    public static ArrayList<UserOrder> getData(int UserID){
        Connection conn = dbConnection.connectionClass();
        ArrayList<UserOrder> list = new ArrayList<>();
        if(conn!=null){
            String sql = "Select * from user_order where user_id ="+UserID;
            try
            {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                while (rs.next()){
                    UserOrder userOrder = new UserOrder();
                    userOrder.setUser_order_id(rs.getInt(1));
                    userOrder.setUser_id(rs.getInt(2));
                    userOrder.setCreatedAt(rs.getTimestamp(3));
                    userOrder.setUpdatedAt(rs.getTimestamp(4));
                    userOrder.setUser_total_values(rs.getInt(5));
                    list.add(userOrder);
                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static boolean addOrder(int UserID) {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = dbConnection.connectionClass();

            String query = "INSERT INTO user_order (user_id, createdAt, updatedAt,user_order_total_value) VALUES (?, ?, ?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, UserID);
            LocalDateTime now = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                now = LocalDateTime.now();
                String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                preparedStatement.setTimestamp(2, Timestamp.valueOf(String.valueOf(formattedDateTime)));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(String.valueOf(formattedDateTime)));
            }
            preparedStatement.setInt(4,0);
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
