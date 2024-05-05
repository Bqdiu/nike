package com.example.nike.Model;

public class ProductSize {
    int productID;
    Size size;
    int soluong;
    boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ProductSize(int productID, Size size, int soluong) {
        this.productID = productID;
        this.size = size;
        this.soluong = soluong;
    }
    public ProductSize(){

    }
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
