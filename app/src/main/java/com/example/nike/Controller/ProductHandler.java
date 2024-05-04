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
            String query = "Select pp.product_parent_name, p.*, pp.product_price from product p inner join product_parent pp on p.product_parent_id = pp.product_parent_id";
           try {
               Statement smt = conn.createStatement();
               ResultSet rs = smt.executeQuery(query);
               while (rs.next()){
                   Product product = new Product();
                   product.setProductID(rs.getInt(2));
                   product.setName(rs.getString(1));
                   product.setProductParentID(rs.getInt(3));
                   product.setMoreInfo(rs.getString(4));
                   product.setImg(rs.getString(5));
                   product.setPrice(rs.getInt(6));
                   product.setSizeAndFit(rs.getString(7));
                   product.setStyleCode(rs.getString(8));
                   product.setColorShown(rs.getString(9));
                   product.setDescription(rs.getString(10));
                   product.setDescription2(rs.getString(11));

                   list.add(product);

               }
               conn.close();
           }catch (SQLException e){
               throw new RuntimeException(e);
           }
        }
        return list;
    }
    public static ArrayList<Product> getDataByParentID(int ParentID) {
        Connection conn = dbConnection.connectionClass();
        ArrayList<Product> list = new ArrayList<>();
        if(conn!=null){
            String query = "select pp.product_parent_name, p.* ,pp.product_price from product p inner join product_parent pp on p.product_parent_id = pp.product_parent_id  inner join product_object po on pp.product_object_id = po.product_object_id where p.product_parent_id ="+ParentID;
            try {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                while (rs.next()){
                    Product product = new Product();
                    product.setName(rs.getString(1));
                    product.setProductID(rs.getInt(2));
                    product.setProductParentID(rs.getInt(3));
                    product.setMoreInfo(rs.getString(4));
                    product.setImg(rs.getString(5));
                    product.setSizeAndFit(rs.getString(6));
                    product.setStyleCode(rs.getString(7));
                    product.setColorShown(rs.getString(8));
                    product.setDescription(rs.getString(9));
                    product.setDescription2(rs.getString(10));
                    product.setPrice(rs.getInt(11));

                    list.add(product);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static Product getDetailProduct(Integer ProductID){
        Product product = new Product();
        Connection conn = dbConnection.connectionClass();
        if (conn!=null){
            String query = "select pp.product_parent_name, p.*  from product p inner join product_parent pp on p.product_parent_id = pp.product_parent_id inner join product_object po on pp.product_object_id = po.product_object_id where p.product_id="+ProductID;
            try
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                product.setProductID(rs.getInt(2));
                product.setName(rs.getString(1));
                product.setProductParentID(rs.getInt(3));
                product.setMoreInfo(rs.getString(4));
                product.setImg(rs.getString(5));
                product.setPrice(rs.getInt(6));
                product.setSizeAndFit(rs.getString(7));
                product.setStyleCode(rs.getString(8));
                product.setColorShown(rs.getString(9));
                product.setDescription(rs.getString(10));
                product.setDescription2(rs.getString(11));


                conn.close();
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

        }
        return product;
    }
}
