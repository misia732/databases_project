package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.Customer;

public class CustomerDAOImpl implements CustomerDAO {

    private Connection conn;

    public CustomerDAOImpl(Connection conn) {
        this.conn = conn;
        Statement stmt;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertCustomer(Customer customer) {
        String query = "INSERT INTO customer (firstName, lastName, gender, birthDate, phoneNumber, email, address, postalCode, city, pizzaCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, String.valueOf(customer.getGender())); // convert char to String
            pstmt.setDate(4, customer.getBirthDate());
            pstmt.setString(5, customer.getPhoneNumber());
            pstmt.setString(6, customer.getEmail());
            pstmt.setString(7, customer.getAdress()); // Corrected spelling
            pstmt.setString(8, customer.getPostalcode()); // Corrected spelling
            pstmt.setString(9, customer.getCity());
            pstmt.setInt(10, customer.getPizzaCount());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
