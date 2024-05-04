package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.ProductParent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductParentHandler {
    private static DBConnection dbConnection = new DBConnection();
    public static ArrayList<ProductParent> getData(){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select * from product_parent";
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    ProductParent pp = new ProductParent();
                    pp.setId(rs.getInt(1));
                    pp.setName(rs.getString(2));
                    pp.setObjectID(rs.getInt(3));
                    pp.setCategoryID(rs.getInt(4));
                    pp.setThumbnail(rs.getString(5));
                    pp.setPrice(rs.getInt(6));
                    pp.setNewRelease(rs.getBoolean(7));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static ArrayList<ProductParent> getDataNewReleaseByObjectID(int ObjectID){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select * from product_parent where is_new_release = 'true' and product_object_id="+ObjectID;
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    ProductParent pp = new ProductParent();
                    pp.setId(rs.getInt(1));
                    pp.setName(rs.getString(2));
                    pp.setObjectID(rs.getInt(3));
                    pp.setCategoryID(rs.getInt(4));
                    pp.setThumbnail(rs.getString(5));
                    pp.setPrice(rs.getInt(6));
                    pp.setNewRelease(rs.getBoolean(7));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

}
