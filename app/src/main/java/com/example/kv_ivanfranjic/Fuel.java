package com.example.kv_ivanfranjic;

public class Fuel {

    private String name, price, id, image, quantity;

    public Fuel() {
    }

    public Fuel(String name, String price, String id, String image, String quantity) {
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

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {

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
