package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.ProductSize;
import com.example.nike.Model.Size;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductSizeHandler {
    private static DBConnection dbConnection = new DBConnection();
    public static ArrayList<ProductSize> getDataByProductID(int productID){
        ArrayList<ProductSize> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select p.product_id, s.size_id, s.size_name , pz.soluong,pz.product_size_id from product_size pz inner join product p on pz.product_id = p.product_id inner join size s on pz.size_id = s.size_id where p.product_id ="+productID;
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    ProductSize productSize = new ProductSize();
                    productSize.setSelect(false);
                    productSize.setProductID(rs.getInt(1));
                    Size size = new Size(rs.getInt(2),rs.getString(3));
                    productSize.setSize(size);
                    productSize.setSoluong(rs.getInt(4));
                    productSize.setProduct_size_id(rs.getInt(5));
                    list.add(productSize);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
}
