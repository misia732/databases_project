package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.OrderItem;

public class OrderItemDAOImpl implements OrderItemDAO {
    private Connection conn;

    public OrderItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(OrderItem orderItem) {
        String query = "INSERT INTO order_items (itemID, quantity) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, orderItem.getItemID());
            pstmt.setInt(2, orderItem.getQuantity());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderItem.setID(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(OrderItem orderItem) {
        if (exists(orderItem.getID())) {
            String query = "DELETE FROM order_items WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, orderItem.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("OrderItem does not exist.");
        }
    }

    @Override
    public void update(OrderItem orderItem) {
        if (exists(orderItem.getID())) {
            String query = "UPDATE order_items SET itemID = ?, quantity = ? WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, orderItem.getItemID());
                pstmt.setInt(2, orderItem.getQuantity());
                pstmt.setInt(3, orderItem.getID());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("OrderItem does not exist.");
        }
    }

    @Override
    public OrderItem findById(int id) {
        String query = "SELECT * FROM order_items WHERE ID = ?";
        OrderItem orderItem = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    orderItem = new OrderItem(
                        rs.getInt("ID"),
                        rs.getInt("itemID"),
                        rs.getInt("quantity")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orderItem;
    }

    private boolean exists(int id) {
        String query = "SELECT COUNT(*) FROM order_items WHERE ID = ?";
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
