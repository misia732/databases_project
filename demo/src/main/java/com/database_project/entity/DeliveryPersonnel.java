package com.database_project.entity;

public class DeliveryPersonnel {
    private Integer ID;
    private String firstName;
    private String lastName;
    private String postalcode;
    private String status;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public DeliveryPersonnel(String firstName, String lastName, String postalcode, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalcode = postalcode;
        this.status = status;
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
        return "DeliveryPersonnel [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", postalcode="
                + postalcode + ", status=" + status + "]";
    }
    
    
}
