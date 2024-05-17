package com.example.nike.Views.Bag.Adapter;


public class BagClass {
    int idImg, quantity, size;
    String name, des, color;
    double price;

    public BagClass() {

    }

    public BagClass(int idImg, String name, double price) {
        this.idImg = idImg;
        this.name = name;
        this.price = price;
    }

    public BagClass(String name, String des, String color,  int size, int quantity, double price, int idImg) {
        this.name = name;
        this.des = des;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.idImg = idImg;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

}
