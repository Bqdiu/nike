package com.example.nike.Model;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Timestamp;

public class UserOrder {
    private int user_order_id;
    private int user_id;
    private Timestamp createdAt,updatedAt;
    private int user_total_values;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserOrder(int user_order_id, int user_id, Timestamp createdAt, Timestamp updatedAt, int user_total_values) {
        this.user_order_id = user_order_id;
        this.user_id = user_id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user_total_values = user_total_values;
    }
    public UserOrder(){}

    public int getUser_order_id() {
        return user_order_id;
    }

    public void setUser_order_id(int user_order_id) {
        this.user_order_id = user_order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getUser_total_values() {
        return user_total_values;
    }

    public void setUser_total_values(int user_total_values) {
        this.user_total_values = user_total_values;
    }
}
