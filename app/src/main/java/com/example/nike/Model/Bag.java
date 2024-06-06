package com.example.nike.Model;

public class Bag {
    private int bagID;
    private Product product;
    private String sizeName;
    private int quantity;
    private int totalPrice;

    public Bag(int bagID, Product product, String sizeName, int quantity, int totalPrice) {
        this.bagID = bagID;
        this.product = product;
        this.sizeName = sizeName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Bag() {

    }

    public int getBagID() {
        return bagID;
    }

    public void setBagID(int bagID) {
        this.bagID = bagID;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
