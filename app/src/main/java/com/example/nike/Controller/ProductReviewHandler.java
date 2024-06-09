package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductReview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ProductReviewHandler {
    private static DBConnection dbConnection = new DBConnection();
    public static ArrayList<ProductReview> getDataByProductID(int productID){
        ArrayList<ProductReview> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = dbConnection.connectionClass();
            String sql = "Select pr.product_review_id,pr.user_id,u.user_email,pr.product_id,pr.product_review_Title,pr.product_review_content,pr.product_review_time,pr.product_review_rate\n" +
                    "from product_review pr\n" +
                    "inner join user_account u on pr.user_id = u.user_id\n" +
                    "where product_id = "+productID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                ProductReview pr = new ProductReview();
                pr.setProductReviewID(rs.getInt(1));
                pr.setUserID(rs.getInt(2));
                pr.setUserEmail(rs.getString(3));
                pr.setProductID(rs.getInt(4));
                pr.setReviewTitle(rs.getString(5));
                pr.setReviewContent(rs.getString(6));
                pr.setReviewTime(rs.getDate(7));
                pr.setReviewRate(rs.getFloat(8));
                list.add(pr);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            try {
                if(conn!=null)
                    conn.close();
                if(stmt!=null)
                    stmt.close();
                if(rs!=null)
                    rs.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static boolean submitReview(int userID, int productID, String reviewTitle, String reviewContent,float reviewRate){
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{
            conn = dbConnection.connectionClass();
            String sql = "INSERT INTO product_review(user_id,product_id,product_review_Title,product_review_content,product_review_time,product_review_rate) VALUES(?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,userID);
            preparedStatement.setInt(2,productID);
            preparedStatement.setString(3,reviewTitle);
            preparedStatement.setString(4,reviewContent);
            LocalDate date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDate.now();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date utilDate = format.parse(date.toString());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                preparedStatement.setDate(5, sqlDate);
            }

            preparedStatement.setFloat(6,reviewRate);
            int result = preparedStatement.executeUpdate();
            return result>0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(conn!=null){
                    conn.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }

    }
    public static boolean checkReviewerExist(int userID,int productID){

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = dbConnection.connectionClass();
            String sql = "select count(*) from product_review where user_id = ? and product_id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2,productID);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
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
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
