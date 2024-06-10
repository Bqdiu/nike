package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.ProductImage;
import com.example.nike.Model.ShopByIcons;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IconsHandler {
    private static DBConnection dbConnection = new DBConnection();

    public static ArrayList<ShopByIcons> getData(){
        Connection conn = dbConnection.connectionClass();
        ArrayList<ShopByIcons> list = new ArrayList<>();
        if(conn!=null){
            String query = "Select * from product_icons";
            try {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                while(rs.next()){
                    ShopByIcons i = new ShopByIcons();
                    i.setId(rs.getInt(1));
                    i.setName(rs.getString(2));
                    i.setThumbnail(rs.getString(3));
                    list.add(i);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }


    public static ArrayList<ShopByIcons> getDataByObjectID(int object_id){
        Connection conn = dbConnection.connectionClass();
        ArrayList<ShopByIcons> list = new ArrayList<>();
        if(conn!=null){
            String query = "Select * from product_icons icon inner join product_parent pp on icon.product_icons_id = pp.product_icons_id where pp.product_object_id = " + object_id;
            try {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                while(rs.next()){
                    ShopByIcons i = new ShopByIcons();
                    i.setId(rs.getInt(1));
                    i.setName(rs.getString(2));
                    i.setThumbnail(rs.getString(3));
                    list.add(i);

                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static ArrayList<Integer> getDataByObjectIDDistinct(int object_id){
        Connection conn = dbConnection.connectionClass();
        ArrayList<Integer> list = new ArrayList<>();
        if(conn!=null){
            String query = "Select distinct icon.product_icons_id from product_icons icon inner join product_parent pp on icon.product_icons_id = pp.product_icons_id where pp.product_object_id = " + object_id;
            try {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                while(rs.next()){
                    int i = rs.getInt(1);
                    list.add(i);
                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static ShopByIcons getDetailByIconId(int icon_id){
        Connection conn = dbConnection.connectionClass();
        ShopByIcons i = new ShopByIcons();
        if(conn!=null){
            String query = "Select * from product_icons where product_icons_id = " + icon_id;
            try {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(query);
                if(rs.next()){
                    i.setId(rs.getInt(1));
                    i.setName(rs.getString(2));
                    i.setThumbnail(rs.getString(3));
                }
                conn.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return i;
    }
}
