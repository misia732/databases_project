package com.database_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.DAO.CustomerDAO;
import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.Model.Customer;
public class dbcTest{
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/resteurant_db?user=root&password=pieseczek71&allowPublicKeyRetrieval=true");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        System.out.println("Connectin succesfull");

        String query = "SHOW FULL TABLES";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Query executed successfully. Retrieving results...");

            // Print out the result set (showing table names)
            while (rs.next()) {
                System.out.println("Table: " + rs.getString(1) + " | Type: " + rs.getString(2));
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        final CustomerDAO customerDAO = new CustomerDAOImpl(conn, false);

        Date birthDate = Date.valueOf("2005-12-31");
        Customer customer = new Customer(1, "Marta", "Adamowska", 'F', birthDate, "+00123456789", "marta@gmail.com", "Cinamon", "1234AB", "Maastricht", 0);

        customerDAO.insertCustomer(customer);
        System.out.println("customer : " + customer.toString() + " inserted");
    }
    
}
