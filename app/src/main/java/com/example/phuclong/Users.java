package com.example.phuclong;

public class Users {
    private  String Password;
    private String Name;
    private String Email;


    public Users(){

    }
    public Users(String pass , String name,String email){
        Password = pass;
        Name = name;
        Email = email;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}