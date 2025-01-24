package com.example.shoppingapp.models;

public class Product {

    private String name;
    private String category;
    private String description;
    private int image;
    private  double price;
    private int id_;

    public Product(String name, String category, String description, int image, double price, int id_) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
        this.id_ = id_;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }
}
