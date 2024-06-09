package com.example.nike.Model;

public class UserOrderProducts {
    private int user_order_id;
    private int product_size_id;
    private Product product;
    private String sizeName;
    private int amount;
    private int totalPrice;
    private int quantity;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public UserOrderProducts(int user_order_id, int product_size_id, Product product, String sizeName, int totalPrice, int quantity) {
        this.user_order_id = user_order_id;
        this.product_size_id = product_size_id;
        this.product = product;
        this.sizeName = sizeName;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

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
