package com.database_project.entity;

public class OrderDrinkAndDesert {
    private int orderID;
    private int drinkAndDesertID;
    private int quantity;
    public OrderDrinkAndDesert(int orderID, int drinkAndDesertID, int quantity) {
        this.orderID = orderID;
        this.drinkAndDesertID = drinkAndDesertID;
        this.quantity = quantity;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public int getDrinkAndDesertID() {
        return drinkAndDesertID;
    }
    public void setDrinkAndDesertID(int drinkAndDesertID) {
        this.drinkAndDesertID = drinkAndDesertID;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "OrderDrinkAndDesert [orderID=" + orderID + ", drinkAndDesertID=" + drinkAndDesertID + ", quantity="
                + quantity + "]";
    }
    
    
}
