package com.database_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import com.database_project.DAO.IngredientDAO;
import com.database_project.DAO.IngredientDAOImpl;
import com.database_project.config.DatabaseConfig;
import com.database_project.entity.Customer;
import com.database_project.entity.DeliveryPersonnel;
import com.database_project.entity.Ingredient;


public class dbcTest{
 public static void main(String[] args) {
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

        // create customers
        Customer[] customers = {
            new Customer("Marta", "Adamowska", "F", Date.valueOf("2005-08-30"), "+48123456789", "marta.adamowska@example.com", "Paul-Henri Spaaklaan 1", "6229EN", "Maastricht", 0),
            new Customer("Misia", "Krawczyk", "F", Date.valueOf("2005-10-30"), "+48111222333", "misia.krawczyk@gmail.com", "Forum 100", "6229GV", "Maastricht",0),
            new Customer("John", "Smith", "M", Date.valueOf("1992-04-15"), "+48987654321", "john.smith@example.com", "Main Street 2", "6229EN", "Maastricht", 1),
            new Customer("Emily", "Johnson", "F", Date.valueOf("1988-12-05"), "+48123412345", "emily.johnson@example.com", "High Street 3", "6229EN", "Maastricht", 2),
            new Customer("Robert", "Williams", "M", Date.valueOf("1975-09-20"), "+48987654322", "robert.williams@example.com", "First Avenue 4", "6229EN", "Maastricht", 3),
            new Customer("Alice", "Brown", "F", Date.valueOf("1995-01-10"), "+48123498765", "alice.brown@example.com", "Second Avenue 5", "6229EN", "Maastricht", 4),
            new Customer("David", "Jones", "M", Date.valueOf("1980-11-30"), "+48987654323", "david.jones@example.com", "Elm Street 6", "6229GV", "Maastricht", 5),
            new Customer("Laura", "Miller", "F", Date.valueOf("1993-06-25"), "+48123454321", "laura.miller@example.com", "Oak Street 7", "6229GV", "Maastricht", 6),
            new Customer("Paul", "Davis", "M", Date.valueOf("1990-03-15"), "+48987654324", "paul.davis@example.com", "Pine Street 8", "6229GV", "Maastricht", 7),
            new Customer("Sara", "Wilson", "F", Date.valueOf("1998-07-22"), "+48123476543", "sara.wilson@example.com", "Maple Street 9", "6229GV", "Maastricht", 8),
            new Customer("Mark", "Moore", "M", Date.valueOf("1985-05-17"), "+48987654325", "mark.moore@example.com", "Birch Street 10", "6229GV", "Maastricht", 9)
        };
        

        // inserting customers
        // try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        //     CustomerDAO customerDAO = new CustomerDAOImpl(conn);
        //     for (Customer customer : customers) {
        //         customerDAO.insert(customer);
        //     }
        // } catch (SQLException e) {
        //     System.out.println("SQLException: " + e.getMessage());
        //     System.out.println("SQLState: " + e.getSQLState());
        //     System.out.println("VendorError: " + e.getErrorCode());
        // }


        // create delivery personel
        DeliveryPersonnel[] deliveryPersonnel = {
            new DeliveryPersonnel("Lucas", "Miller", "6229GV"),
            new DeliveryPersonnel("Emma", "Smith", "6229GV"),
            new DeliveryPersonnel("Oliver", "Johnson", "6229GV"),
            new DeliveryPersonnel("Sophia", "Williams", "6229GV"),
            new DeliveryPersonnel("Liam", "Brown", "6229EN"),
            new DeliveryPersonnel("Ava", "Jones", "6229EN"),
            new DeliveryPersonnel("Noah", "Garcia", "6229EN"),
            new DeliveryPersonnel("Isabella", "Martinez", "6229GV"),
            new DeliveryPersonnel("Elijah", "Davis", "6229GV"),
            new DeliveryPersonnel("Mia", "Rodriguez", "6229EN")
        };

        // inserting delivery personel
        // try (Connection conn = DatabaseConfig.getConnection()) {
        //     DeliveryPersonnelDAO customerDAO = new DeliveryPersonnelDAOImpl(conn);
        //     for (DeliveryPersonnel DeliveryPersonnel_ : deliveryPersonnel) {
        //         customerDAO.insert(DeliveryPersonnel_);
        //     }
        // } catch (SQLException e) {
        //     System.out.println("SQLException: " + e.getMessage());
        //     System.out.println("SQLState: " + e.getSQLState());
        //     System.out.println("VendorError: " + e.getErrorCode());
        // }

        // creating ingredients
        Ingredient[] ingredients = {
            new Ingredient("Tomato", 0.50, true, true),
            new Ingredient("Cheese", 1.00, true, false),
            new Ingredient("Lettuce", 0.30, true, true),
            new Ingredient("Chicken", 2.50, false, false),
            new Ingredient("Olive Oil", 3.00, true, true),
            new Ingredient("Eggplant", 1.20, true, true),
            new Ingredient("Ham", 3.50, false, false),
            new Ingredient("Tomato Sauce", 2.00, true, true),
            new Ingredient("Spinach", 4.00, true, true),
            new Ingredient("Burrata", 1.80, true, false),
            new Ingredient("Basil", 3.7, true, true)

        };

        // inserting ingredients
        try (Connection conn = DatabaseConfig.getConnection()) {
            IngredientDAO ingredientDAO = new IngredientDAOImpl(conn);
            for (Ingredient ingredient : ingredients) {
                ingredientDAO.insert(ingredient);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        
    }
}
