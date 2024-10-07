package com.database_project.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.Random;

import com.database_project.DAO.CustomerDAO;
import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.DAO.DiscountCodeDAO;
import com.database_project.DAO.DiscountCodeDAOImpl;
import com.database_project.entity.Customer;
import com.database_project.entity.DiscountCode;

public class CustomerService {

    private Connection conn;
    private CustomerDAO customerDAO;
    private DiscountCodeDAO discountCodeDAO;

    public CustomerService(Connection conn) {
        this.conn = conn;
        this.customerDAO = new CustomerDAOImpl(conn);
        this.discountCodeDAO = new DiscountCodeDAOImpl(conn);
    }

    public void createAccount(String firstName, String lastName, String gender, Date birthDate, String phoneNumber,
                                String email, String password, String address, String postalcode, String city, int pizzaCount){
        Customer customer = new Customer(firstName, lastName, gender, birthDate, phoneNumber, email, password, address, postalcode, city, pizzaCount);
        customerDAO.insert(customer);
    }

    public Customer login(String email, String password) {
        Customer customer = customerDAO.findByEmail(email);
        if(customer == null){
            System.out.println("Account does not exist.");
            return null;
        }
        if(!customer.getPassword().equals(password)){
            System.out.println("Incorrect passwort.");
            return null;
        }
        // check for birthday discount
        long millis = System.currentTimeMillis();  
        java.sql.Date today = new java.sql.Date(millis);  
        if(today == customer.getBirthDate()){
            DiscountCode birthdayDiscountCode = new DiscountCode(generateRandomCode(), false, 0, 0);
            //discountCodeDAO.insert(discountCode);
        }
        return customer;
    }

    private String generateRandomCode(){
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 8) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            code.append(CHARS.charAt(index));
        }
        return code.toString();
    }
}
