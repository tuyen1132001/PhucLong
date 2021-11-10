package com.example.phuclong;

public class User {
    private  String Password;
    private String Name;


    public User(){

    }
    public User(String Pass , String name){
        Password = Pass;
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}