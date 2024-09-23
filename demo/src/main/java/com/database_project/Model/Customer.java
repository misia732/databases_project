package com.database_project.Model;

import java.sql.Date;

public class Customer{

    public Customer(int ID, String firstName, String lastName, char gender, Date birthDate, String phoneNumber,
            String email, String adress, String postalcode, String city, int pizzaCount) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.adress = adress;
        this.postalcode = postalcode;
        this.city = city;
        this.pizzaCount = pizzaCount;
    }

    private int ID;
    private String firstName;
    private String lastName;
    private char gender;
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private String adress;
    private String postalcode;
    private String city;
    private int pizzaCount;

    public void setID(int ID){
        this.ID = ID;
    }
    public int getID() {
        return ID;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
        return "Customer [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
                + ", birthDate=" + birthDate + ", phoneNumber=" + phoneNumber + ", email=" + email + ", adress="
                + adress + ", postalcode=" + postalcode + ", city=" + city + ", pizzaCount=" + pizzaCount + "]";
    }
}