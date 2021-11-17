package com.example.phuclong;

public class User {
    private  String Password;
    private String Name;
    private String Phone;


    public User(){

    }

    public User(String password, String name) {
        Password = password;
        Name = name;

    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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