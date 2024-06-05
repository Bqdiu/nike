package com.example.nike.Model;

public class ProductSize {
    int product_size_id;
    int productID;
    Size size;
    int soluong;
    boolean isSelect;

    public int getProduct_size_id() {
        return product_size_id;
    }

    public void setProduct_size_id(int product_size_id) {
        this.product_size_id = product_size_id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ProductSize(int product_size_id, int productID, Size size, int soluong, boolean isSelect) {
        this.product_size_id = product_size_id;
        this.productID = productID;
        this.size = size;
        this.soluong = soluong;
        this.isSelect = isSelect;
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
