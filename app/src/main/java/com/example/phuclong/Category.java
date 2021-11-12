package com.example.phuclong;

public class Category {
    String  Name;
    String Image;
    public  Category(){

    }
    public Category(String name , String image){
        Name = name;
        Image = image;
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
}
