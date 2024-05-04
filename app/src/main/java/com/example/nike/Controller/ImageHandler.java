package com.example.nike.Controller;

import android.media.Image;

import com.example.nike.Model.CategoryProduct;
import com.example.nike.Model.DBConnection;
import com.example.nike.Model.ProductImage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ImageHandler {
    private static DBConnection dbConnection = new DBConnection();

    public static ArrayList<ProductImage> getData(){
        Connection conn = dbConnection.connectionClass();
        ArrayList<ProductImage> list = new ArrayList<>();
        if(conn!=null){
            String query = "Select * from product_img";
            try {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                while(rs.next()){
                    ProductImage i = new ProductImage();
                    i.setId(rs.getInt(1));
                    i.setProductID(rs.getInt(2));
                    i.setFileName(rs.getString(3));
                    list.add(i);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static ArrayList<ProductImage> getPhotoByProductID(int productID){
        Connection conn = dbConnection.connectionClass();
        ArrayList<ProductImage> list = new ArrayList<>();
        if(conn!=null){
            String query = "Select * from product_img where product_id="+productID;
            try {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                while(rs.next()){
                    ProductImage i = new ProductImage();
                    i.setId(rs.getInt(1));
                    i.setProductID(rs.getInt(2));
                    i.setFileName(rs.getString(3));
                    list.add(i);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
}
