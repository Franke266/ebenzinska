package com.example.kv_ivanfranjic;

public class Fuel {

    private String name, id, image, quantity;
    private Double price;

    public Fuel() {
    }

    public Fuel(String name, Double price, String id, String image, String quantity) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.image = image;
        this.quantity = quantity;
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

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public String getQuantity() {

        return quantity;
    }

    public void setQuantity(String quantity) {

        this.quantity = quantity;
    }


}
