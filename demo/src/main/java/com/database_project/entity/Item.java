package com.database_project.entity;

public class Item {
    private int ID;
    private String name;
    private double price;
    private boolean isVegeterian;
    private boolean isVegan;
    private String itemType;
    public Item(int iD, String name, double price, boolean isVegeterian, boolean isVegan, String itemType) {
        ID = iD;
        this.name = name;
        this.price = price;
        this.isVegeterian = isVegeterian;
        this.isVegan = isVegan;
        this.itemType = itemType;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public boolean isVegeterian() {
        return isVegeterian;
    }
    public void setVegeterian(boolean isVegeterian) {
        this.isVegeterian = isVegeterian;
    }
    public boolean isVegan() {
        return isVegan;
    }
    public void setVegan(boolean isVegan) {
        this.isVegan = isVegan;
    }
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    @Override
    public String toString() {
        return "Item [ID=" + ID + ", name=" + name + ", price=" + price + ", isVegeterian=" + isVegeterian
                + ", isVegan=" + isVegan + ", itemType=" + itemType + "]";
    }
    
}
