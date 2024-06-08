package com.example.nike.Model;

import net.sourceforge.jtds.jdbc.DateTime;

import java.util.Date;

public class ProductReview {
    private int productReviewID;
    private int userID;
    private String userEmail;
    private int productID;
    private String reviewTitle;
    private String reviewContent;
    private Date reviewTime;
    private float reviewRate;

    public ProductReview(int productReviewID, int userID, String userEmail, int productID, String reviewTitle, String reviewContent, Date reviewTime, float reviewRate) {
        this.productReviewID = productReviewID;
        this.userID = userID;
        this.userEmail = userEmail;
        this.productID = productID;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewTime = reviewTime;
        this.reviewRate = reviewRate;
    }

    public ProductReview() {

    }

    public int getProductReviewID() {
        return productReviewID;
    }

    public void setProductReviewID(int productReviewID) {
        this.productReviewID = productReviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public float getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(float reviewRate) {
        this.reviewRate = reviewRate;
    }
}
