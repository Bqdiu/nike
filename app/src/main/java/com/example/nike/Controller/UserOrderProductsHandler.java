package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.Product;
import com.example.nike.Model.UserOrderProducts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserOrderProductsHandler {
    private static DBConnection dbConnection = new DBConnection();
    public static boolean insertOrder(int user_order_id,int product_size_id,int amount)
    {
        Connection conn = dbConnection.connectionClass();
        String sql = "insert into user_order_products (user_order_id,product_size_id,amount) values (?,?,?)";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,user_order_id);
            preparedStatement.setInt(2,product_size_id);
            preparedStatement.setInt(3,amount);
            int rowInserted = preparedStatement.executeUpdate();
            return rowInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<UserOrderProducts> getUserOrderProducts(int userId) {
        Connection conn = dbConnection.connectionClass();
        ResultSet rs = null;
        ArrayList<UserOrderProducts> list = new ArrayList<>();
        if (conn != null) {
            String sql = "SELECT uop.user_order_id, uop.product_size_id, pp.product_parent_name, po.product_object_name, " +
                    "cp.category_product_name, p.*, s.size_name, uop.amount, (uop.amount * pp.product_price) as total_price, uo.createdAt " +
                    "FROM user_order_products uop " +
                    "INNER JOIN product_size ps ON uop.product_size_id = ps.product_size_id " +
                    "INNER JOIN product p ON ps.product_id = p.product_id " +
                    "INNER JOIN size s ON ps.size_id = s.size_id " +
                    "INNER JOIN product_parent pp ON p.product_parent_id = pp.product_parent_id " +
                    "INNER JOIN product_object po ON pp.product_object_id = po.product_object_id " +
                    "INNER JOIN category_product cp ON pp.product_category_id = cp.category_product_id " +
                    "INNER JOIN user_order uo ON uop.user_order_id = uo.user_order_id " +
                    "WHERE uo.user_id = " + userId;
            try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    UserOrderProducts userOrderProduct = new UserOrderProducts();
                    userOrderProduct.setUser_order_id(rs.getInt(1));
                    userOrderProduct.setProduct_size_id(rs.getInt(2));

                    Product product = new Product();
                    product.setName(rs.getString(3));
                    product.setObjectName(rs.getString(4));
                    product.setCategoryName(rs.getString(5));
                    product.setProductID(rs.getInt(6));
                    product.setProductParentID(rs.getInt(7));
                    product.setMoreInfo(rs.getString(8));
                    product.setImg(rs.getString(9));
                    product.setSizeAndFit(rs.getString(10));
                    product.setStyleCode(rs.getString(11));
                    product.setColorShown(rs.getString(12));
                    product.setDescription(rs.getString(13));
                    product.setDescription2(rs.getString(14));
                    userOrderProduct.setProduct(product);
                    userOrderProduct.setSizeName(rs.getString(15));
                    userOrderProduct.setAmount(rs.getInt(16));
                    userOrderProduct.setTotalPrice(rs.getInt(17));
                    userOrderProduct.setDate(rs.getString(18));

                    list.add(userOrderProduct);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return list;
    }

    public static ArrayList<UserOrderProducts> getUserOrderProductsByUserOrderID(int userId, int user_order_id) {
        Connection conn = dbConnection.connectionClass();
        ResultSet rs = null;
        ArrayList<UserOrderProducts> list = new ArrayList<>();
        if (conn != null) {
            String sql = "SELECT uop.user_order_id, uop.product_size_id, pp.product_parent_name, po.product_object_name, " +
                    "cp.category_product_name, p.*, s.size_name, uop.amount, (uop.amount * pp.product_price) as total_price, uo.createdAt " +
                    "FROM user_order_products uop " +
                    "INNER JOIN product_size ps ON uop.product_size_id = ps.product_size_id " +
                    "INNER JOIN product p ON ps.product_id = p.product_id " +
                    "INNER JOIN size s ON ps.size_id = s.size_id " +
                    "INNER JOIN product_parent pp ON p.product_parent_id = pp.product_parent_id " +
                    "INNER JOIN product_object po ON pp.product_object_id = po.product_object_id " +
                    "INNER JOIN category_product cp ON pp.product_category_id = cp.category_product_id " +
                    "INNER JOIN user_order uo ON uop.user_order_id = uo.user_order_id " +
                    "WHERE uo.user_id = " + userId + " and uo.user_order_id = " + user_order_id;
            try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    UserOrderProducts userOrderProduct = new UserOrderProducts();
                    userOrderProduct.setUser_order_id(rs.getInt(1));
                    userOrderProduct.setProduct_size_id(rs.getInt(2));

                    Product product = new Product();
                    product.setName(rs.getString(3));
                    product.setObjectName(rs.getString(4));
                    product.setCategoryName(rs.getString(5));
                    product.setProductID(rs.getInt(6));
                    product.setProductParentID(rs.getInt(7));
                    product.setMoreInfo(rs.getString(8));
                    product.setImg(rs.getString(9));
                    product.setSizeAndFit(rs.getString(10));
                    product.setStyleCode(rs.getString(11));
                    product.setColorShown(rs.getString(12));
                    product.setDescription(rs.getString(13));
                    product.setDescription2(rs.getString(14));
                    userOrderProduct.setProduct(product);
                    userOrderProduct.setSizeName(rs.getString(15));
                    userOrderProduct.setAmount(rs.getInt(16));
                    userOrderProduct.setTotalPrice(rs.getInt(17));
                    userOrderProduct.setDate(rs.getString(18));

                    list.add(userOrderProduct);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return list;
    }
}
