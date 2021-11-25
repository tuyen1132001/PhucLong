package com.example.phuclong.model;

public class OrderAD {
   String Address;
    String Status;
    String OrderID;
   String Sdt;
    String Total;

    public OrderAD(String orderID,String address, String status , String sdt,String total ){
        OrderID = orderID;
        Address = address;
        Status = status;
        Sdt = sdt;
        Total=total;

    }
    public String getOrderID() { return OrderID; }

    public void setOrderID(String orderID) { OrderID= orderID; }

    public String getAddress() { return Address;}
    public void setAddress(String address ) {Address = address ;}

    public String getStatus() { return Status;}
    public void setStatus(String status ) {Status = status ;}

    public String getSdt() { return Sdt;}
    public void setSdt(String sdt ) {Sdt = sdt ;}

    public String getTotal() { return Total;}
    public void setTotal(String total ) {Total = total ;}
}

