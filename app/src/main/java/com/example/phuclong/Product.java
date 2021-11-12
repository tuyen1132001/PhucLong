package com.example.phuclong;

public class Product {
   static String  Name,Image,Description,Price,Discourt,Categoryid;
    public  Product(){

    }
    public Product(String name , String image,String description,String price,String discourt,String categoryid){
        Name = name;
        Image = image;
        Description= description;
        Price=price;
        Discourt=discourt;
        Categoryid=categoryid;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public String getDiscourt() {
        return Discourt;
    }

    public String getPrice() {
        return Price;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDiscourt(String discourt) {
        Discourt = discourt;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
