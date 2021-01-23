package com.example.kv_ivanfranjic;

public class Equipment {
    private String name, price, image, id, description, quantity;

    public Equipment() {
    }

    public Equipment(String name, String price, String image, String id, String description, String quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {

        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
