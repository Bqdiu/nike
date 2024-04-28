package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductHandler {
    private static DBConnection dbConnection = new DBConnection();

    public static ArrayList<Product> getData() {
        Connection conn = dbConnection.connectionClass();
        ArrayList<Product> list = new ArrayList<>();
        if(conn!=null){
            String query = "select * from product";
           try {
               Statement smt = conn.createStatement();
               ResultSet rs = smt.executeQuery(query);
               while (rs.next()){
                   Product product = new Product();
                   product.setProductID(rs.getInt(1));
                   product.setProductParentID(rs.getInt(2));
                   product.setMoreInfo(rs.getString(3));
                   product.setImg(rs.getString(4));
                   product.setPrice(rs.getInt(5));
                   product.setSizeAndFit(rs.getString(6));
                   product.setStyleCode(rs.getString(7));
                   product.setColorShown(rs.getString(8));
                   product.setDescription(rs.getString(9));
                   product.setDescription2(rs.getString(10));
                   list.add(product);
                   conn.close();
               }
           }catch (SQLException e){
               throw new RuntimeException(e);
           }
        }
        return list;
    }
}
