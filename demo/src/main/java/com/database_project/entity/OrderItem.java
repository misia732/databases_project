package com.database_project.entity;

public class OrderItem {
    private int ID;
    private int itemID;
    private int quantity;
    public OrderItem(int iD, int itemID, int quantity) {
        ID = iD;
        this.itemID = itemID;
        this.quantity = quantity;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "OrderItem [ID=" + ID + ", itemID=" + itemID + ", quantity=" + quantity + "]";
    }
    
}
