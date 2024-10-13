package com.database_project.entity;

import java.sql.Date;

public class Customer{

    public Customer(String firstName, String lastName, String gender, Date birthDate, String phoneNumber,
            String email, String password, String address, String postalcode, String city, int pizzaCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.postalcode = postalcode;
        this.city = city;
        this.pizzaCount = pizzaCount;
        this.password = password;
    }

    private int ID;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private String phoneNumber;
    private String email;   // unique for every customer
    private String address;
    private String postalcode;
    private String city;
    private int pizzaCount;
    private String password;    // unique for every customer

    private boolean isBirthday = false;

    

    public boolean isBirthday() {
        return isBirthday;
    }

    public void setBirthday(boolean isBirthday) {
        this.isBirthday = isBirthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPizzaCount() {
        return pizzaCount;
    }

    public void setPizzaCount(int pizzaCount) {
        this.pizzaCount = pizzaCount;
    }

    @Override
    public String toString() {
        return "Customer [id=" + ID + ", firtsName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
                + ", birthDate=" + birthDate + ", phoneNumber=" + phoneNumber + ", email=" + email + ", password=" + password + ", adress="
                + address + ", postalcode=" + postalcode + ", city=" + city + ", pizzaCount=" + pizzaCount + "]";
    }
}