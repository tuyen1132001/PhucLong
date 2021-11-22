package com.example.phuclong.ui.InforCustomer;

public class InforlistDomain {
    private String id;
    private String email;
    private String name;
    private String img;

    public InforlistDomain(String id, String email, String name, String img) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
