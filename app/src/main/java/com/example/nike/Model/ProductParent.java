package com.example.nike.Model;

public class ProductParent {
    int id;
    String name;
    int objectID;
    int categoryID;
    String thumbnail;
    int price;
    Boolean isNewRelease;

    public ProductParent(int id, String name, int objectID, int categoryID, String thumbnail, int price, Boolean isNewRelease) {
        this.id = id;
        this.name = name;
        this.objectID = objectID;
        this.categoryID = categoryID;
        this.thumbnail = thumbnail;
        this.price = price;
        this.isNewRelease = isNewRelease;
    }
    public ProductParent(){}

    public Boolean getNewRelease() {
        return isNewRelease;
    }

    public void setNewRelease(Boolean newRelease) {
        isNewRelease = newRelease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
