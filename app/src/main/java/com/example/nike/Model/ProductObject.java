package com.example.nike.Model;

public class ProductObject {
    int objectID;
    String objectName;

    public ProductObject(int objectID, String objectName) {
        this.objectID = objectID;
        this.objectName = objectName;
    }
    public ProductObject(){}

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
