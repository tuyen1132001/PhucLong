package com.example.phuclong;

import com.example.phuclong.User;

public class Common {
    public  static User currentUser;
    public  static final String UPDATE = "Update";
    public  static final String DELETE = "Delete";

    public static  String convertCodeToStatus(String code){
        if (code.equals("0"))
            return "Chờ Duyệt";
        else if (code.equals("1"))
            return "Đã Duyệt";
        else
            return "Hoàn Thành";
    }
}
