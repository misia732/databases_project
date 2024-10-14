package com.database_project.DAO;

import java.time.LocalDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.database_project.entity.Order;
import com.database_project.entity.Pizza;

public class OrderDAOImpl implements OrderDAO {

    private Connection conn;

    public OrderDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int insert(Order order) {
        String query = "INSERT INTO `order` (customerID, placementTime, status, deliveryPersonnelID, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getCustomerID());
    
            // Set placementTime
            if (order.getPlacementTime() == null)
                pstmt.setNull(2, java.sql.Types.TIMESTAMP);
            else
                pstmt.setTimestamp(2, Timestamp.valueOf(order.getPlacementTime()));
    
            // Set status
            pstmt.setString(3, order.getStatus());
    
            // Set deliveryPersonnelID
            if (order.getDeliveryPersonnelID() == null)
                pstmt.setNull(4, java.sql.Types.INTEGER);
            else
                pstmt.setInt(4, order.getDeliveryPersonnelID());
    
            // Set price
            if (order.getPrice() == null)
                pstmt.setNull(5, java.sql.Types.DOUBLE);
            else
                pstmt.setDouble(5, order.getPrice());
    
            int affectedRows = pstmt.executeUpdate();
    
            // Check if rows were affected and get the generated key
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        order.setID(id);  
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return -1;
    }
    



    @Override
    public void delete(Order order) {
        String query = "DELETE FROM `order` WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE `order` SET customerID = ?, placementTime = ?, status = ?, deliveryPersonnelID = ?, price = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getCustomerID());
    
            if (order.getPlacementTime() == null) {
                pstmt.setNull(2, java.sql.Types.TIMESTAMP);
            } else {
                pstmt.setTimestamp(2, Timestamp.valueOf(order.getPlacementTime()));
            }
    
            if (order.getStatus() == null) {
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            } else {
                pstmt.setString(3, order.getStatus());
            }
            
            if (order.getDeliveryPersonnelID() == null) {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(4, order.getDeliveryPersonnelID());
            }
    
            if (order.getPrice() == null) {
                pstmt.setNull(5, java.sql.Types.DOUBLE);
            } else {
                pstmt.setDouble(5, order.getPrice());
            }
    
            pstmt.setInt(6, order.getID());
    
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order with ID " + order.getID() + " updated successfully.");
            } else {
                System.out.println("No order found with ID " + order.getID());
            }
        } catch (SQLException e) {
            System.err.println("Update Error: " + e.getMessage());
        }
    }
    

    @Override
    public Order findByID(int id) {
        if(!existsById(id)){
            System.out.println("Order does not exist.");
            return null;
        }
        String query = "SELECT * FROM `order` WHERE ID = ?";
        Order order = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int customerID = rs.getInt("customerID");

                    LocalDateTime placementTime = null;
                    Timestamp placementTimeStamp = rs.getTimestamp("placementTime"); // Use getTimestamp directly
                    if (placementTimeStamp != null) {
                        placementTime = placementTimeStamp.toLocalDateTime(); // Convert Timestamp to LocalDateTime
                    }

                    String status = null;
                    Object statusObject = rs.getObject("status");
                    if (statusObject != null) {
                        status = (String) statusObject;
                    }

                    Integer deliveryPersonnelID = null;
                    Object deliveryPersonnelIDObject = rs.getObject("deliveryPersonnelID");
                    if (deliveryPersonnelIDObject != null) {
                        deliveryPersonnelID = (Integer) deliveryPersonnelIDObject;
                    }

                    Double price = null;
                    Object priceObject = rs.getObject("price");
                    if (priceObject != null) {
                        price = (Double) priceObject;
                    }

                    order = new Order(customerID, placementTime, status, deliveryPersonnelID, price);
                    order.setID(id);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    private boolean existsById(int id) {
        String query = "SELECT COUNT(*) FROM `order` WHERE ID = ?";
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
    

    @Override
    public List<Order> findOrdersByStatus(String status){
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `order` WHERE status = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("customerID"),
                    rs.getTimestamp("placementTime").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getInt("deliveryPersonnelID"),
                    rs.getDouble("price")
                );
                order.setID(rs.getInt("ID"));
                orders.add(order);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return orders;
    }

    @Override
    public List<Order> findOrdersByPostalcodeAndTime(String postalCode, LocalDateTime timeWindow){
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `order` "
                 + "JOIN customer ON order.customerID = customer.ID "
                 + "WHERE customer.postalCode = ? AND order.placementTime >= ? AND order.status = 'being prepared'";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, postalCode);
            stmt.setTimestamp(2, Timestamp.valueOf(timeWindow));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("customerID"),
                    rs.getTimestamp("placementTime").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getInt("deliveryPersonnelID"),
                    rs.getDouble("price")
                );
                orders.add(order);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public boolean hasPendingOrder(int customerId) {
        String query = "SELECT COUNT(*) FROM `order` WHERE customerID = ? AND status != 'delivered'";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if there are pending orders
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false; // No pending orders
    }

    public int findOrderIDbyCustomerID(int customerId) {
        String query = "SELECT ID FROM `order` WHERE customerID = ? AND status != 'delivered' LIMIT 1";
    
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID"); // Return the first undelivered order ID
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return -1; 
    }
    
}
