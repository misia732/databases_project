package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.Customer;

public class CustomerDAOImpl implements CustomerDAO {

    private Connection conn;

    public CustomerDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Customer customer) {
    if (!customerExistsByEmail(customer.getEmail())) {
        String query = "INSERT INTO customer (firstName, lastName, gender, birthDate, phoneNumber, email, password, address, postalCode, city, pizzaCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getGender());
            pstmt.setDate(4, customer.getBirthDate()); // Ensure this is a java.sql.Date
            pstmt.setString(5, customer.getPhoneNumber());
            pstmt.setString(6, customer.getEmail());
            pstmt.setString(7, customer.getPassword()); // Set password here
            pstmt.setString(8, customer.getAddress());
            pstmt.setString(9, customer.getPostalcode());
            pstmt.setString(10, customer.getCity());
            pstmt.setInt(11, customer.getPizzaCount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        customer.setID(id);
                    }
                }
            }
            System.out.println("Customer: " + customer.toString() + " inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return;
    } else {
        System.out.println("Customer already exists.");
    }
}


    

    @Override
    public void delete(Customer customer){
        if(customerExistsByEmail(customer.getEmail())){
            String query = "DELETE FROM customer WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)){
                pstmt.setInt(1, customer.getID());
            int affectedRows = pstmt.executeUpdate();  // Returns the number of rows affected
            
            if (affectedRows > 0) {
                System.out.println("Customer deleted: " + customer.toString());
            } else {
                System.out.println("No customer deleted. Check if the ID exists.");
            }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Customer does not exist.");
        }

    }

    @Override
    public void update(Customer customer){
        String query = "UPDATE customer SET firstName = ?, lastName = ?, gender = ?, birthDate = ?, phoneNumber = ?, email = ?, password = ?, address = ?, postalCode = ?, city = ?, pizzaCount = ? WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getGender());
            pstmt.setDate(4, customer.getBirthDate());
            pstmt.setString(5, customer.getPhoneNumber());
            pstmt.setString(6, customer.getEmail());
            pstmt.setString(7, customer.getPassword());
            pstmt.setString(8, customer.getAddress());
            pstmt.setString(9, customer.getPostalcode());
            pstmt.setString(10, customer.getCity());
            pstmt.setInt(11, customer.getPizzaCount());
            pstmt.setString(12, customer.getEmail());

            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Customer findByEmail(String email) {
        if (customerExistsByEmail(email)) {
            String query = "SELECT * FROM customer WHERE email = ?";
            Customer customer; 
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            customer = new Customer(
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("gender"),
                                rs.getDate("birthDate"),
                                rs.getString("phoneNumber"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("address"),
                                rs.getString("postalCode"),
                                rs.getString("city"),
                                rs.getInt("pizzaCount")
                            );
                            customer.setID(rs.getInt("ID"));
                        }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return customer; // Return the populated customer object
        }
        System.out.println("Customer does not exist.");
        return null; // Return null if the customer does not exist
    }
    

    @Override
    public Customer findByID(int id){
        if(customerExistsByID(id)){
            String query = "SELECT * FROM customer WHERE ID = ?";
            Customer customer = null;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        customer = new Customer(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getDate("birthDate"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address"),
                        rs.getString("postalCode"),
                        rs.getString("city"),
                        rs.getInt("pizzaCount")
                    );
                        customer.setID(rs.getInt("ID"));
                    }
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return customer;
        }
        System.out.println("Coustomer does not exist.");
        return null;
    }

    private boolean customerExistsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM customer WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean customerExistsByID(int id) {
        String query = "SELECT COUNT(*) FROM customer WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
