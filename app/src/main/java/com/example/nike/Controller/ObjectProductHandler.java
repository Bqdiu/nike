package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.ProductObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ObjectProductHandler {
    private static DBConnection dbConnection = new DBConnection();
    public static ArrayList<ProductObject> getData(){
        Connection conn = dbConnection.connectionClass();
        ArrayList<ProductObject> list = new ArrayList<>();
        if(conn!=null){
            String query = "select * from product_object";
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    ProductObject p = new ProductObject();
                    p.setObjectID(rs.getInt(1));
                    p.setObjectName(rs.getString(2));
                    list.add(p);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
}
