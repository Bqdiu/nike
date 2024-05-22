package com.example.nike.Model;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    Connection conn = null;
    String username,password,  ip, port, database;
    public Connection connectionClass(){
        ip="192.168.56.1";
        database="db_BanQuanAo";
        username= "sa";
        password="123456";
        port="1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String ConnectionStr = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionStr = "jdbc:jtds:sqlserver://"+ip+":"+port+";databaseName="+database+";user="+username+";password="+password+";encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(ConnectionStr);
//            System.out.println("Ket noi duoc roi ne");

        }catch (Exception ex){
            Log.e("Error",ex.getMessage());
        }
        return conn;
    }

    public boolean addUser(String username, String password, String gender, String email, String phoneNumber, String address, String firstName, String lastName, int memberTier, int point) {
        boolean isSuccess = false;
        try {
            // Xây dựng câu truy vấn INSERT
            String query = "INSERT INTO user_account (user_username, user_password, user_gender, user_email, user_phone_number, user_address, user_first_name, user_last_name, user_member_tier, user_point) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Chuẩn bị câu lệnh SQL
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

            // Thực thi câu lệnh INSERT
            int rowsInserted = preparedStatement.executeUpdate();

            // Kiểm tra xem có dòng nào được chèn thành công không
            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }
}
