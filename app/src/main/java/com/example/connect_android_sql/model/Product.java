package com.example.connect_android_sql.model;

public class Product {

    String productName;
    String productDescription;
    int price;
    int productQuantity;
    int categoryId;
    boolean isAdmin;

    public Product() {
    }

    public Product(String productName, String productDescription, int price, int productQuantity, int categoryId, boolean isAdmin) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.productQuantity = productQuantity;
        this.categoryId = categoryId;
        this.isAdmin = isAdmin;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
