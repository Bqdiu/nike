package com.example.nike.Controller;

import android.os.Build;

import com.example.nike.Model.Bag;
import com.example.nike.Model.DBConnection;
import com.example.nike.Model.Product;
import com.example.nike.Views.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BagHandler {
    private static DBConnection dbConnection = new DBConnection();

    public static ArrayList<Bag> getBag(int user_id){
        Connection conn = dbConnection.connectionClass();
        ResultSet rs = null;
        ArrayList<Bag> list = new ArrayList<>();
        if(conn!=null) {
            String sql = "select b.bag_id,b.product_size_id, pp.product_parent_name, po.product_object_name,cp.category_product_name,p.*, s.size_name, b.amount, (b.amount * pp.product_price) as total_price\n" +
                    "from bag b\n" +
                    "inner join product_size ps on b.product_size_id = ps.product_size_id\n" +
                    "inner join product p on ps.product_id = p.product_id\n" +
                    "inner join size s on ps.size_id = s.size_id\n" +
                    "inner join product_parent pp on p.product_parent_id = pp.product_parent_id\n" +
                    "inner join product_object po on pp.product_object_id = po.product_object_id\n" +
                    "inner join category_product cp on pp.product_category_id = cp.category_product_id\n" +
                    "where user_id ="+user_id;
            try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()){
                    Bag bag = new Bag();
                    bag.setBagID(rs.getInt(1));
                    bag.setProductSizeID(rs.getInt(2));
                    Product product = new Product();
                    product.setName(rs.getString(3));
                    product.setCategoryName(rs.getString(4));
                    product.setObjectName(rs.getString(5));
                    product.setProductID(rs.getInt(6));
                    product.setProductParentID(rs.getInt(7));
                    product.setMoreInfo(rs.getString(8));
                    product.setImg(rs.getString(9));
                    product.setSizeAndFit(rs.getString(10));
                    product.setStyleCode(rs.getString(11));
                    product.setColorShown(rs.getString(12));
                    product.setDescription(rs.getString(13));
                    product.setDescription2(rs.getString(14));

                    bag.setProduct(product);
                    bag.setSizeName(rs.getString(15));
                    bag.setQuantity(rs.getInt(16));
                    bag.setTotalPrice(rs.getInt(17));

                    list.add(bag);
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
    public static boolean isExists(int user_id, int product_size_id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.connectionClass();
            String sql = "select count(*) from bag where user_id = ? and product_size_id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, product_size_id); // Corrected index to 2
            rs = preparedStatement.executeQuery();
            if (rs.next()) { // Changed to if
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
    public static boolean updateQuantity(int user_id,int bag_id,int quantity){
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = dbConnection.connectionClass();

            String query = "UPDATE bag SET amount = ? where user_id = ? and bag_id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, bag_id);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
    public static boolean increaseQuantity(int user_id,int product_size_id){
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = dbConnection.connectionClass();

            String query = "UPDATE bag SET amount = amount + 1 where user_id = ? and product_size_id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, product_size_id);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
    public static boolean deleteProduct(int bag_id){
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = dbConnection.connectionClass();

            String query = "DELETE FROM bag where bag_id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, bag_id);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
    public static boolean addToBag(int user_id,int product_size_id,int quantity) {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = dbConnection.connectionClass();

            String query = "INSERT INTO bag (user_id,product_size_id,amount) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, product_size_id);
            preparedStatement.setInt(3,quantity);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
    public static boolean removeAll(int user_id) {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = dbConnection.connectionClass();

            String query = "DELETE FROM bag where user_id=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
}
