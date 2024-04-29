package com.example.nike.Controller;

import com.example.nike.Model.CategoryProduct;
import com.example.nike.Model.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryHandler {
    private static DBConnection dbConnection = new DBConnection();
    private static Connection conn = dbConnection.connectionClass();
    public static ArrayList<CategoryProduct> getData(){

        ArrayList<CategoryProduct> list = new ArrayList<>();
        if(conn!=null){
            String query = "Select * from category_product";
            try{
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                while(rs.next()){
                    CategoryProduct category = new CategoryProduct();
                    category.setCategoryID(rs.getInt(1));
                    category.setName(rs.getString(2));
                    category.setDescription(rs.getString(3));
                    list.add(category);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }finally {
                try {
                    conn.close(); // Đóng kết nối sau khi sử dụng
                } catch (SQLException e) {
                    e.printStackTrace(); // Xử lý ngoại lệ khi không thể đóng kết nối
                }
            }
        }
        return list;

    }

}


