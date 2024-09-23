package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.Model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

    private Connection conn;

    public CustomerDAOImpl(Connection conn, boolean dropTable) {
        this.conn = conn;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            if (dropTable) {
                stmt.executeUpdate("DROP TABLE IF EXISTS users"); // Adjust table name if necessary
            }
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "firstName VARCHAR(64), "
                + "lastName VARCHAR(64), "
                + "gender CHAR(1), "
                + "birthDate DATE, "
                + "phoneNumber VARCHAR(15), "
                + "email VARCHAR(64), "
                + "address VARCHAR(255), "
                + "postalCode VARCHAR(10), "
                + "city VARCHAR(64), "
                + "pizzaCount INT, "
                + "PRIMARY KEY (ID))"); // Use ID as the primary key
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public void insertCustomer(Customer customer) {
        String query = "INSERT INTO users (ID, firstName, lastName, gender, birthDate, phoneNumber, email, address, postalCode, city, pizzaCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customer.getID());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, String.valueOf(customer.getGender())); // convert char to String
            pstmt.setDate(5, customer.getBirthDate());
            pstmt.setString(6, customer.getPhoneNumber());
            pstmt.setString(7, customer.getEmail());
            pstmt.setString(8, customer.getAdress()); // Corrected spelling
            pstmt.setString(9, customer.getPostalcode()); // Corrected spelling
            pstmt.setString(10, customer.getCity());
            pstmt.setInt(11, customer.getPizzaCount());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
