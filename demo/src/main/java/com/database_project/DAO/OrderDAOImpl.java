package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.Order;

public class OrderDAOImpl implements OrderDAO {

    private Connection conn;

    public OrderDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Order order) {
        String query = "INSERT INTO orders (customerID, placemenDate, placementTime, pickedUpDate, pickedUpTime, status, price, discountCodeID, deliveryPersonelID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setDate(2, order.getPlacemenDate());
            pstmt.setTime(3, order.getPlacementTime());
            pstmt.setDate(4, order.getPickedUpDate());
            pstmt.setTime(5, order.getPickedUpTime());
            pstmt.setString(6, order.getStatus());
            pstmt.setDouble(7, order.getPrice());
            pstmt.setString(8, order.getDiscountCodeID());
            pstmt.setInt(9, order.getDeliveryPersonelID());

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
        String query = "DELETE FROM orders WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE orders SET customerID = ?, placemenDate = ?, placementTime = ?, pickedUpDate = ?, pickedUpTime = ?, status = ?, price = ?, discountCodeID = ?, deliveryPersonelID = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setDate(2, order.getPlacemenDate());
            pstmt.setTime(3, order.getPlacementTime());
            pstmt.setDate(4, order.getPickedUpDate());
            pstmt.setTime(5, order.getPickedUpTime());
            pstmt.setString(6, order.getStatus());
            pstmt.setDouble(7, order.getPrice());
            pstmt.setString(8, order.getDiscountCodeID());
            pstmt.setInt(9, order.getDeliveryPersonelID());
            pstmt.setInt(10, order.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Order findById(int id) {
        String query = "SELECT * FROM orders WHERE ID = ?";
        Order order = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    order = new Order(
                        rs.getInt("ID"),
                        rs.getInt("customerID"),
                        rs.getDate("placemenDate"),
                        rs.getTime("placementTime"),
                        rs.getDate("pickedUpDate"),
                        rs.getTime("pickedUpTime"),
                        rs.getString("status"),
                        rs.getDouble("price"),
                        rs.getString("discountCodeID"),
                        rs.getInt("deliveryPersonelID")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }
}
