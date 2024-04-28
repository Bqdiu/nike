package com.example.nike.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryProduct {
    int categoryID;
    String name;
    String description;

    public CategoryProduct(int categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public CategoryProduct(){}

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
