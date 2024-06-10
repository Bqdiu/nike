package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.ProductParent;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductParentHandler implements Serializable {
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
                    pp.setIconsID(rs.getInt(8));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static ArrayList<ProductParent> getAllNewRelease(){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select * from product_parent where is_new_release = 'true'";
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
                    pp.setIconsID(rs.getInt(8));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static ArrayList<ProductParent> getAllClothing(){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select * from product_parent where product_category_id = 1";
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
                    pp.setIconsID(rs.getInt(8));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }


    public static ArrayList<ProductParent> getAllClothingByObjectID(int object_id){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select * from product_parent where product_category_id = 1 and product_object_id = " + object_id;
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
                    pp.setIconsID(rs.getInt(8));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static ArrayList<ProductParent> getAllProductParentByIcon(int icon_id){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select * from product_parent where product_icons_id = " + icon_id;
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
                    pp.setIconsID(rs.getInt(8));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static ArrayList<ProductParent> getAllProductParentByIconAndObjectID(int icon_id, int object_id){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = dbConnection.connectionClass();
        if(conn!=null){
            String query = "select * from product_parent where product_icons_id = " + icon_id + " and product_object_id = " + object_id;
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
                    pp.setIconsID(rs.getInt(8));
                    list.add(pp);
                }
            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static ArrayList<ProductParent> getProductParentByName(String name){
        ArrayList<ProductParent> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.connectionClass();
            if (conn != null) {
                String query = "SELECT * FROM product_parent WHERE product_parent_name LIKE ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + name + "%");

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ProductParent pp = new ProductParent();
                    pp.setId(rs.getInt(1));
                    pp.setName(rs.getString(2));
                    pp.setObjectID(rs.getInt(3));
                    pp.setCategoryID(rs.getInt(4));
                    pp.setThumbnail(rs.getString(5));
                    pp.setPrice(rs.getInt(6));
                    pp.setNewRelease(rs.getBoolean(7));
                    pp.setIconsID(rs.getInt(8));
                    list.add(pp);
                }
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String getNameProductParent(int product_id)
    {
        String productParentName = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnection.connectionClass();
            if (conn != null) {
                String query = "SELECT pp.product_parent_name " +
                        "FROM product p " +
                        "JOIN product_parent pp ON p.product_parent_id = pp.product_parent_id " +
                        "WHERE p.product_id = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, product_id);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    productParentName = rs.getString("product_parent_name");
                }
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productParentName;
    }
}
