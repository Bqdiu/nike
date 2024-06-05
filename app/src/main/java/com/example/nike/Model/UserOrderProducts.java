package com.example.nike.Model;

public class UserOrderProducts {
    private int user_order_id;
    private int product_size_id;
    private int amount;

    public UserOrderProducts(int user_order_id, int product_size_id, int amount) {
        this.user_order_id = user_order_id;
        this.product_size_id = product_size_id;
        this.amount = amount;
    }
    public UserOrderProducts(){}

    public int getUser_order_id() {
        return user_order_id;
    }

    public void setUser_order_id(int user_order_id) {
        this.user_order_id = user_order_id;
    }

    public int getProduct_size_id() {
        return product_size_id;
    }

    public void setProduct_size_id(int product_size_id) {
        this.product_size_id = product_size_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
