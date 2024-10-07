package com.database_project.entity;

public class DrinkAndDesert {
    private int ID;
    private String name;
    private double price;
    public DrinkAndDesert(int iD, String name, double price) {
        ID = iD;
        this.name = name;
        this.price = price;
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
    @Override
    public String toString() {
        return "DrinkAndDesert [ID=" + ID + ", name=" + name + ", price=" + price + "]";
    }
    
}
