package com.database_project.entity;

public class Pizza {
    @Override
    public String toString() {
        return "Pizza [ID=" + ID + ", name=" + name + "]";
    }
    private int ID;
    private String name;
    public Pizza(String name) {
        this.name = name;
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

    
}
