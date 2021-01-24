package com.example.kv_ivanfranjic;

public class Cart {
    private String id, name, quantity, image;
    private Double price;

    public Cart() {
    }

    public Cart(String id, String name, Double price, String quantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image= image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

