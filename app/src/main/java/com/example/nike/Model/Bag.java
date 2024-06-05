package com.example.nike.Model;

public class Bag {

    private ProductSize productSize;
    private int quantity;

    public Bag(ProductSize productSize, int quantity) {
        this.productSize = productSize;
        this.quantity = quantity;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
