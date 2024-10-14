package com.database_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database_project.DAO.CustomerDAO;
import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.DAO.DeliveryPersonnelDAO;
import com.database_project.DAO.DeliveryPersonnelDAOImpl;
import com.database_project.config.DatabaseConfig;
import com.database_project.entity.Customer;
import com.database_project.entity.DeliveryPersonnel;
import com.database_project.entity.Ingredient;
import com.database_project.entity.OrderDrinkAndDesert;
import com.database_project.entity.OrderPizza;
import com.database_project.service.OrderService;


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
            new Customer("Misia", "Krawczyk", "Female", Date.valueOf("2005-10-30"), "+48111222333", "misia.krawczyk@gmail.com", "jsjwo3ihne", "Forum 100", "6229GV", "Maastricht",0),
            new Customer("John", "Smith", "Male", Date.valueOf("1992-04-15"), "+48987654321", "john.smith@example.com", "wjub3ue", "Main Street 2", "6229EN", "Maastricht", 1),
            new Customer("Emily", "Johnson", "Female", Date.valueOf("1988-12-05"), "+48123412345", "emily.johnson@example.com", "ki2h2ine", "High Street 3", "6229EN", "Maastricht", 2),
            new Customer("Robert", "Williams", "Male", Date.valueOf("1975-09-20"), "+48987654322", "robert.williams@example.com", "kwni2eo", "First Avenue 4", "6229EN", "Maastricht", 3),
            new Customer("Alice", "Brown", "Female", Date.valueOf("1995-01-10"), "+48123498765", "alice.brown@example.com", "29eidin", "Second Avenue 5", "6229EN", "Maastricht", 4),
            new Customer("David", "Jones", "Male", Date.valueOf("1980-11-30"), "+48987654323", "david.jones@example.com", "293eindom", "Elm Street 6", "6229GV", "Maastricht", 5),
            new Customer("Laura", "Miller", "Female", Date.valueOf("1993-06-25"), "+48123454321", "laura.miller@example.com", "kji293n", "Oak Street 7", "6229GV", "Maastricht", 6),
            new Customer("Paul", "Davis", "Male", Date.valueOf("1990-03-15"), "+48987654324", "paul.davis@example.com", "i2heuibd", "Pine Street 8", "6229GV", "Maastricht", 7),
            new Customer("Sara", "Wilson", "Female", Date.valueOf("1998-07-22"), "+48123476543", "sara.wilson@example.com", "j23ueu", "Maple Street 9", "6229GV", "Maastricht", 8),
            new Customer("Mark", "Moore", "Male", Date.valueOf("1985-05-17"), "+48987654325", "mark.moore@example.com", "ih38b", "Birch Street 10", "6229GV", "Maastricht", 9),
            new Customer("", "", "", Date.valueOf("1989-05-17"), "", "test", "1", "", "", "", 0)
        };
        

        // inserting customers
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
        // try (Connection conn = DatabaseConfig.getConnection()) {
        //     IngredientDAO ingredientDAO = new IngredientDAOImpl(conn);
        //     for (Ingredient ingredient : ingredients) {
        //         ingredientDAO.insert(ingredient);
        //     }
        // } catch (SQLException e) {
        //     System.out.println("SQLException: " + e.getMessage());
        //     System.out.println("SQLState: " + e.getSQLState());
        //     System.out.println("VendorError: " + e.getErrorCode());
        // }
        
        // try(Connection conn = DatabaseConfig.getConnection())
        // {
        //     CustomerDAO customerDAO = new CustomerDAOImpl(conn);
        //     CustomerService customerService = new CustomerService(conn);
        //     OrderDAO orderDAO = new OrderDAOImpl(conn);
        //     OrderService orderService = new OrderService(conn);
        //     int customerID = 13;
        //     int orderID = orderService.initializeNewOrder(customerID);
        //     //int orderID = 1;
        //     Order order = orderDAO.findByID(orderID);
        //     System.out.println(order.toString());
        //     OrderPizza orderPizza = new OrderPizza(orderID, 1, 2);
        //     List<OrderPizza> pizzas = new ArrayList<>();
        //     pizzas.add(orderPizza);
        //     OrderDrinkAndDesert drink = new OrderDrinkAndDesert(orderID, 6, 1);
        //     List<OrderDrinkAndDesert> drinksAndDeserts = new ArrayList<>();
        //     drinksAndDeserts.add(drink);
        //     orderService.placeOrder(orderID, pizzas, drinksAndDeserts, "");
        //     LocalDateTime now = LocalDateTime.now();
        //     order.setPlacementTime(now);
        //     orderDAO.update(order);
        //     orderService.cancelOrder(orderID);
        // }
        // catch (SQLException e) {
        //         System.out.println("SQLException: " + e.getMessage());
        //         System.out.println("SQLState: " + e.getSQLState());
        //         System.out.println("VendorError: " + e.getErrorCode());
        // };

        // try(Connection conn = DatabaseConfig.getConnection())
        // {
        //     //CustomerDAO customerDAO = new CustomerDAOImpl(conn);
        //     //CustomerService customerService = new CustomerService(conn);
        //     OrderDAO orderDAO = new OrderDAOImpl(conn);
        //     OrderService orderService = new OrderService(conn);
        //     int customerID = 1;
        //     int orderID = orderService.initializeNewOrder(customerID);
        //     //int orderID = 1;
        //     Order order = orderDAO.findByID(orderID);
        //     System.out.println(order.toString());
        //     OrderPizza orderPizza = new OrderPizza(orderID, 1, 2);
        //     List<OrderPizza> pizzas = new ArrayList<>();
        //     pizzas.add(orderPizza);
        //     OrderDrinkAndDesert drink = new OrderDrinkAndDesert(orderID, 6, 1);
        //     List<OrderDrinkAndDesert> drinksAndDeserts = new ArrayList<>();
        //     drinksAndDeserts.add(drink);
        //     orderService.placeOrder(orderID, pizzas, drinksAndDeserts, "");
        //     LocalDateTime now = LocalDateTime.now();
        //     order.setPlacementTime(now);
        //     orderDAO.update(order);
        //     //orderService.cancelOrder(orderID);
        // }
        // catch (SQLException e) {
        //         System.out.println("SQLException: " + e.getMessage());
        //         System.out.println("SQLState: " + e.getSQLState());
        //         System.out.println("VendorError: " + e.getErrorCode());
        // };

        try(Connection conn = DatabaseConfig.getConnection())
        {
            // CustomerDAO customerDAO = new CustomerDAOImpl(conn);
            // Customer c = customerDAO.findByID(2);
            // c.setPizzaCount(0);
            // customerDAO.update(c);
            OrderService orderService = new OrderService(conn);
            int id = orderService.initializeNewOrder(2);
            List <OrderPizza> orderPizza = new ArrayList<>();
            OrderPizza o = new com.database_project.entity.OrderPizza(id, 1, 9);
            List<OrderDrinkAndDesert> d = new ArrayList<>();
            orderPizza.add(o);
            orderService.placeOrder(id, orderPizza, d, "");
        }
        catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
        };

        // try(Connection conn = DatabaseConfig.getConnection())
        // {
        //     OrderDAO orderDAO = new OrderDAOImpl(conn);
        //     Order order = orderDAO.findByID(1);
        //     DeliveryService deliveryService = new DeliveryService(conn);
        //     deliveryService.assignDeliveryPersonnel(order);
        // }
        // catch (SQLException e) {
        //         System.out.println("SQLException: " + e.getMessage());
        //         System.out.println("SQLState: " + e.getSQLState());
        //         System.out.println("VendorError: " + e.getErrorCode());
        // };


    
    }
}
