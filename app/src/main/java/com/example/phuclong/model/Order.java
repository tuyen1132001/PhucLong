package com.example.phuclong.model;

public class Order {

     String Image;
    String ProductName;
     String Quantity;
     String Price;
     String IdCart;
     String id;

    public Order()
    {
    }

    public Order( String productName, String image, String quantity, String price , String idCart, String id) {
        ProductName = productName;
        Image = image;
        Quantity = quantity;
        Price = price;
        IdCart = idCart;
        this.id = id;

    }



    public String getIdCart() {
        return IdCart;
    }

    public void setIdCart(String idCart) {
        IdCart = idCart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) { Image = image; }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }



}
