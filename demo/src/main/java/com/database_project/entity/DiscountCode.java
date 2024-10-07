package com.database_project.entity;

public class DiscountCode {
    @Override
    public String toString() {
        return "DiscountCode [ID=" + ID + ", isUsed=" + isUsed + ", percentage=" + percentage + ", customerID=" + customerID + "]";
    }
    private String ID;
    private boolean isUsed;
    private int percentage;
    private int customerID;

    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public DiscountCode(String iD, boolean isUsed, int percentage, int customerID) {
        ID = iD;
        this.isUsed = isUsed;
        this.percentage = percentage;
        this.customerID = customerID;
    }
    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public boolean isUsed() {
        return isUsed;
    }
    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    public int getPercentage() {
        return percentage;
    }
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
    
    
}
