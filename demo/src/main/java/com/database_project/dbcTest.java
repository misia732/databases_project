package com.database_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import com.database_project.DAO.CustomerDAO;
import com.database_project.DAO.CustomerDAOImpl;
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
            new Customer("M&Ms", "Pizzeria", "Female", Date.valueOf("2024-09-01"), "123456789", "admin", "password", "Maastricht University", "", "Maastricht",0),
            new Customer("Mark", "Moore", "Male", Date.valueOf("1985-05-17"), "+48987654325", "firstCustomer", "firstcustomer", "Birch Street 10", "6229GV", "Maastricht", 10),
            new Customer("Misia", "Krawczyk", "Female", Date.valueOf("1985-05-17"), "+48987654325", "discount10pizzas", "discount10pizzas", "Birch Street 10", "6229GV", "Maastricht", 10),
            new Customer("Sara", "Wilson", "Female", Date.valueOf("1998-07-22"), "+48123476543", "birthdayDiscount", "birthdaydiscount", "Maple Street 9", "6229GV", "Maastricht", 2),
            new Customer("Laura", "Miller", "Female", Date.valueOf("1993-06-25"), "+48123454321", "discountCode", "discountcode", "Oak Street 7", "6229GV", "Maastricht", 6),
            new Customer("John", "Smith", "Male", Date.valueOf("1992-04-15"), "+48987654321", "samePostalCode1", "samepostalcode1", "Main Street 2", "6229EN", "Maastricht", 1),
            new Customer("John", "Smith", "Male", Date.valueOf("1992-04-15"), "+48987654321", "samePostalCode2", "samepostalcode2", "Main Street 2", "6229EN", "Maastricht", 1)
        };
        

        // inserting customers
        /*
        try (Connection conn = DatabaseConfig.getConnection()){
            CustomerDAO customerDAO = new CustomerDAOImpl(conn);
            for (Customer customer : customers) {
                customerDAO.insert(customer);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        */

        // create delivery personel
        DeliveryPersonnel[] deliveryPersonnel = {
            new DeliveryPersonnel("Lucas", "Miller", "6229GV", "avaliable"),
            new DeliveryPersonnel("Emma", "Smith", "6229GV", "avaliable"),
            new DeliveryPersonnel("Oliver", "Johnson", "6229GV", "avaliable"),
            new DeliveryPersonnel("Sophia", "Williams", "6211WH", "avaliable"),
            new DeliveryPersonnel("Liam", "Brown", "6229EN", "avaliable"),
            new DeliveryPersonnel("Ava", "Jones", "6229EN", "avaliable"),
            new DeliveryPersonnel("Noah", "Garcia", "6229EN", "avaliable"),
            new DeliveryPersonnel("Isabella", "Martinez", "6211WH", "avaliable"),
            new DeliveryPersonnel("Elijah", "Davis", "6211WH", "avaliable"),
            new DeliveryPersonnel("Mia", "Rodriguez", "6229EN", "avaliable")
        };

        // inserting delivery personel
        /*
        try (Connection conn = DatabaseConfig.getConnection()) {
            DeliveryPersonnelDAO deliveryPersonnelDAO = new DeliveryPersonnelDAOImpl(conn);
            for (DeliveryPersonnel DeliveryPersonnel_ : deliveryPersonnel) {
                deliveryPersonnelDAO.insert(DeliveryPersonnel_);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        */

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

        //inserting ingredients
        /*
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
        */

        try (Connection conn = DatabaseConfig.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAOImpl(conn);
            Customer c = customerDAO.findByEmail("discountCode");
            c.setAddress("Oak Street 7");
            // c.setPizzaCount(0);
            customerDAO.update(c);
            // OrderService orderService = new OrderService(conn);
            // orderService.cancelOrder(17);
            // orderService.cancelOrder(18);
            // int id = orderService.initializeNewOrder(2);
            // List<OrderPizza> orderPizza = new ArrayList<>();
            // orderPizza.add(new OrderPizza(id, 3, 2));
            // List<OrderDrinkAndDesert> orderdd = new ArrayList<>();
            // orderdd.add(new OrderDrinkAndDesert(id,1,1));
            // double price = orderService.placeOrder(id, orderPizza, orderdd, "");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

    }
}
