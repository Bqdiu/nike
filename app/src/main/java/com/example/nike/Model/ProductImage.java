package com.example.nike.Model;

public class ProductImage {
    int id;
    int productID;
    String fileName;

    public ProductImage(int id, int productID, String fileName) {
        this.id = id;
        this.productID = productID;
        this.fileName = fileName;
    }
    public ProductImage(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
