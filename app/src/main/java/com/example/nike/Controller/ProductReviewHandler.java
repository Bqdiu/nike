package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductReview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
}
