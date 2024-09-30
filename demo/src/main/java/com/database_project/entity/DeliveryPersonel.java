package com.database_project.entity;

public class DeliveryPersonel {
    private int ID;
    private String firstName;
    private String lastName;
    private String postalcode;
    public DeliveryPersonel(int iD, String firstName, String lastName, String postalcode) {
        ID = iD;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalcode = postalcode;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPostalcode() {
        return postalcode;
    }
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
    @Override
    public String toString() {
        return "DeliveryPersonel [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", postalcode="
                + postalcode + "]";
    }
    
    
}