package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database_project.entity.DrinkAndDesert;
import com.database_project.entity.OrderDrinkAndDesert;
import com.database_project.entity.Pizza;

public class OrderDrinkAndDesertDAOImpl implements OrderDrinkAndDesertDAO {

    private Connection conn;

    public OrderDrinkAndDesertDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(OrderDrinkAndDesert orderDrinkAndDesert) {
        String query = "INSERT INTO orderDrinkAndDesert (orderID, drinkAndDesertID, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderDrinkAndDesert.getOrderID());
            pstmt.setInt(2, orderDrinkAndDesert.getDrinkAndDesertID());
            pstmt.setInt(3, orderDrinkAndDesert.getQuantity());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("OrderDrinkAndDesert: " + orderDrinkAndDesert.toString() + " inserted");
            }
        } catch (SQLException e) {
            System.out.println("Insertion Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(OrderDrinkAndDesert orderDrinkAndDesert) {
        String query = "DELETE FROM orderDrinkAndDesert WHERE orderID = ? AND drinkAndDesertID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderDrinkAndDesert.getOrderID());
            pstmt.setInt(2, orderDrinkAndDesert.getDrinkAndDesertID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("OrderDrinkAndDesert: " + orderDrinkAndDesert.toString() + " deleted");
            }
        } catch (SQLException e) {
            System.out.println("Deletion Error: " + e.getMessage());
        }
    }

    @Override
    public void update(OrderDrinkAndDesert orderDrinkAndDesert) {
        String query = "UPDATE orderDrinkAndDesert SET quantity = ? WHERE orderID = ? AND drinkAndDesertID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderDrinkAndDesert.getQuantity());
            pstmt.setInt(2, orderDrinkAndDesert.getOrderID());
            pstmt.setInt(3, orderDrinkAndDesert.getDrinkAndDesertID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("OrderDrinkAndDesert: " + orderDrinkAndDesert.toString() + " updated");
            }
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }

    @Override
    public List<OrderDrinkAndDesert> findByOrderID(int orderID) {
        String query = "SELECT * FROM orderDrinkAndDesert WHERE orderID = ?";
        List<OrderDrinkAndDesert> orderDrinkAndDesertList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OrderDrinkAndDesert orderDrinkAndDesert = new OrderDrinkAndDesert(
                        rs.getInt("orderID"),
                        rs.getInt("drinkAndDesertID"),
                        rs.getInt("quantity")
                    );
                    orderDrinkAndDesertList.add(orderDrinkAndDesert);
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return orderDrinkAndDesertList;
    }

    @Override
    public List<DrinkAndDesert> listDrinkAndDeserts(int orderID) {
        String query = "SELECT drinkAndDesert.ID, drinkAndDesert.name, drinkAndDesert.price" +
                    "FROM drinkAndDesert " +
                    "JOIN orderDrinkAndDesert ON drinkAndDesert.ID = orderDrinkAndDesert.drinkAndDesertID " +
                    "WHERE orderDrinkAndDesert.orderID = ?";
        List<DrinkAndDesert> drinksAndDeserts = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DrinkAndDesert drinkAndDesert = new DrinkAndDesert(rs.getString("name"), rs.getInt("price"));
                    drinkAndDesert.setID(rs.getInt("ID"));
                    drinksAndDeserts.add(drinkAndDesert);
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return drinksAndDeserts;
    }
}
