package com.database_project.entity;

import java.time.LocalDateTime;
import java.util.List;


public class Order {
    private int ID;
    private int customerID;
    private LocalDateTime placementTime;
    private String status;
    private Integer deliveryPersonnelID;
    private Double price;

    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }


    public Order(int customerID, LocalDateTime placementTime, String status, Integer deliveryPersonelID, Double price) {
        this.customerID = customerID;
        this.placementTime = placementTime;
        this.status = status;
        this.deliveryPersonnelID = deliveryPersonelID;
        this.price = price;
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
    public LocalDateTime getPlacementTime() {
        return placementTime;
    }
    public void setPlacementTime(LocalDateTime deliveryTime) {
        this.placementTime = deliveryTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getDeliveryPersonnelID() {
        return deliveryPersonnelID;
    }
    public void setDeliveryPersonnelID(Integer deliveryPersonelID) {
        this.deliveryPersonnelID = deliveryPersonelID;
    }

    @Override
    public String toString() {
        return "Order [ID=" + ID + ", customerID=" + customerID + ", placementTime=" + placementTime + ", status="
                + status + ", deliveryPersonnelID=" + deliveryPersonnelID + ", price=" + price + "]";
    }
    
}
