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
    public void insert(Order order) {
        String query = "INSERT INTO order (customerID, placementTime, status, deliveryPersonelID, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setTimestamp(2, Timestamp.valueOf(order.getPlacementTime()));
            pstmt.setString(3, order.getStatus());
            pstmt.setInt(4, order.getDeliveryPersonnelID());
            pstmt.setDouble(5, order.getPrice());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setID(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Order order) {
        String query = "DELETE FROM order WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE order SET customerID = ?, placementTime = ?, status = ?, deliveryPersonelID = ?, price = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setTimestamp(2, Timestamp.valueOf(order.getPlacementTime()));
            pstmt.setString(3, order.getStatus());
            pstmt.setInt(4, order.getDeliveryPersonnelID());
            pstmt.setInt(5, order.getID());
            

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Order findById(int id) {
        String query = "SELECT * FROM order WHERE ID = ?";
        Order order = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    order = new Order(
                        rs.getInt("customerID"),
                        rs.getTimestamp("placementTime").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getInt("deliveryPersonelID"),
                        rs.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    @Override
    public List<Order> findOrdersByStatus(String status){
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE status = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("customerID"),
                    rs.getTimestamp("placementTime").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getInt("deliveryPersonelID"),
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
        String query = "SELECT * FROM orders "
                 + "JOIN customers ON order.customerID = customer.ID "
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
                    rs.getInt("deliveryPersonelID"),
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
}
