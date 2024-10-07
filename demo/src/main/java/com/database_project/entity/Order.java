package com.database_project.entity;

import java.time.LocalDateTime;
import java.util.List;


public class Order {
    private int ID;
    private int customerID;
    private LocalDateTime deliveryTime;
    private String status;
    private String discountCodeID;
    private int deliveryPersonnelID;
    private List<OrderPizza> pizzas;
    private List<OrderDrinkAndDesert> drinksAndDeserts;

    public Order(int customerID, LocalDateTime deliveryTime, String status, String discountCodeID, int deliveryPersonelID) {
        this.customerID = customerID;
        this.deliveryTime = deliveryTime;
        this.status = status;
        this.discountCodeID = discountCodeID;
        this.deliveryPersonnelID = deliveryPersonelID;
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
    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDiscountCodeID() {
        return discountCodeID;
    }
    public void setDiscountCodeID(String discountCodeID) {
        this.discountCodeID = discountCodeID;
    }
    public int getDeliveryPersonnelID() {
        return deliveryPersonnelID;
    }
    public void setDeliveryPersonnelID(int deliveryPersonelID) {
        this.deliveryPersonnelID = deliveryPersonelID;
    }

    @Override
    public String toString() {
        return "Order [ID=" + ID + ", customerID=" + customerID + ", deliveryTime=" + deliveryTime + ", status="
                + status + ", discountCodeID=" + discountCodeID + ", deliveryPersonnelID="
                + deliveryPersonnelID + "]";
    }
    
}
