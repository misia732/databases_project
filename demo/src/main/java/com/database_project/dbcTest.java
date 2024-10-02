package com.database_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.database_project.DAO.CustomerDAO;
import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.DAO.GeneralDAO;
import com.database_project.DAO.GeneralDAOImpl;


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
        System.out.println("Connection succesfull");

        // execute query SHOW TABLES
        /*
        String query = "SHOW FULL TABLES";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Query executed successfully. Retrieving results...");
            // Print out table names
            while (rs.next()) {
                System.out.println("Table: " + rs.getString(1) + " | Type: " + rs.getString(2));
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        */

        // add customer
        final CustomerDAO customerDAO = new CustomerDAOImpl(conn);

        

        final GeneralDAO generalDAO = new GeneralDAOImpl(conn);
        generalDAO.delete("customer");

    }
}
