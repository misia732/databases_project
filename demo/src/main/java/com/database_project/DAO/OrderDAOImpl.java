package com.database_project.DAO;

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
        String query = "INSERT INTO order (customerID, deliveryTime, status, discountCodeID, deliveryPersonelID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setTimestamp(2, Timestamp.valueOf(order.getDeliveryTime()));
            pstmt.setString(3, order.getStatus());
            pstmt.setString(4, order.getDiscountCodeID());
            pstmt.setInt(5, order.getDeliveryPersonnelID());

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
        String query = "UPDATE order SET customerID = ?, placemenDate = ?, placementTime = ?, pickedUpDate = ?, pickedUpTime = ?, status = ?, discountCodeID = ?, deliveryPersonelID = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setTimestamp(2, Timestamp.valueOf(order.getDeliveryTime()));
            pstmt.setString(3, order.getStatus());
            pstmt.setString(4, order.getDiscountCodeID());
            pstmt.setInt(5, order.getDeliveryPersonnelID());
            pstmt.setInt(6, order.getID());

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
                        rs.getTimestamp("deliveryTime").toLocalDateTime(),
                        rs.getString("status"),
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
