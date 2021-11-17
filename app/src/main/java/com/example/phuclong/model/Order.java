package com.example.phuclong.model;

public class Order {

     String Image;
     String ProductName;
     String Quantity;
     String Price;
     String Discount;



    public Order( String productName, String image, String quantity, String price, String discount) {
        ProductName = productName;
        Image = image;
        Quantity = quantity;
        Price = price;
        Discount = discount;
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

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
