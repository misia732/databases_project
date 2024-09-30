package com.database_project.entity;

import java.sql.Date;
import java.sql.Time;

public class Order {
    private int ID;
    private int customerID;
    private Date placemenDate;
    private Time placementTime;
    private Date pickedUpDate;
    private Time pickedUpTime;
    private String status;
    private double price;
    private String discountCodeID;
    private int deliveryPersonelID;

    public Order(int iD, int customerID, Date placemenDate, Time placementTime, Date pickedUpDate, Time pickedUpTime,
            String status, double price, String discountCodeID, int deliveryPersonelID) {
        ID = iD;
        this.customerID = customerID;
        this.placemenDate = placemenDate;
        this.placementTime = placementTime;
        this.pickedUpDate = pickedUpDate;
        this.pickedUpTime = pickedUpTime;
        this.status = status;
        this.price = price;
        this.discountCodeID = discountCodeID;
        this.deliveryPersonelID = deliveryPersonelID;
    }


    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public Date getPlacemenDate() {
        return placemenDate;
    }
    public void setPlacemenDate(Date placemenDate) {
        this.placemenDate = placemenDate;
    }
    public Time getPlacementTime() {
        return placementTime;
    }
    public void setPlacementTime(Time placementTime) {
        this.placementTime = placementTime;
    }
    public Date getPickedUpDate() {
        return pickedUpDate;
    }
    public void setPickedUpDate(Date pickedUpDate) {
        this.pickedUpDate = pickedUpDate;
    }
    public Time getPickedUpTime() {
        return pickedUpTime;
    }
    public void setPickedUpTime(Time pickedUpTime) {
        this.pickedUpTime = pickedUpTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDiscountCodeID() {
        return discountCodeID;
    }
    public void setDiscountCodeID(String discountCodeID) {
        this.discountCodeID = discountCodeID;
    }
    public int getDeliveryPersonelID() {
        return deliveryPersonelID;
    }
    public void setDeliveryPersonelID(int deliveryPersonelID) {
        this.deliveryPersonelID = deliveryPersonelID;
    }

    @Override
    public String toString() {
        return "Order [ID=" + ID + ", customerID=" + customerID + ", placemenDate=" + placemenDate + ", placementTime="
                + placementTime + ", pickedUpDate=" + pickedUpDate + ", pickedUpTime=" + pickedUpTime + ", status="
                + status + ", price=" + price + ", discountCodeID=" + discountCodeID + ", deliveryPersonelID="
                + deliveryPersonelID + "]";
    }
    
}
