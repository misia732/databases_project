package com.database_project.entity;

public class DiscountCode {
    @Override
    public String toString() {
        return "DiscountCode [ID=" + ID + ", isUsed=" + isUsed + ", percentage=" + percentage + "]";
    }
    private String ID;
    private boolean isUsed;
    private int percentage;
    public DiscountCode(String iD, boolean isUsed, int percentage) {
        ID = iD;
        this.isUsed = isUsed;
        this.percentage = percentage;
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
