package com.example.phuclong.model;

public class ModelAcc {
    String ID, Email, Name, Password, Phone, Role;
    public ModelAcc(){

    }

    public ModelAcc(String id, String email, String name, String password, String phone, String role) {
        ID = id;
        Email = email;
        Name = name;
        Password = password;
        Phone = phone;
        Role = role;
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
