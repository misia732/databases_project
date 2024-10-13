package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database_project.entity.OrderPizza;
import com.database_project.entity.Pizza;

public class OrderPizzaDAOImpl implements OrderPizzaDAO {

    private Connection conn;

    public OrderPizzaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(OrderPizza orderPizza) {
        String query = "INSERT INTO orderPizza (orderID, pizzaID, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderPizza.getOrderID());
            pstmt.setInt(2, orderPizza.getPizzaID());
            pstmt.setInt(3, orderPizza.getQuantity());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("OrderPizza: " + orderPizza.toString() + " inserted");
            }
        } catch (SQLException e) {
            System.out.println("Insertion Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(OrderPizza orderPizza) {
        String query = "DELETE FROM orderPizza WHERE orderID = ? AND pizzaID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderPizza.getOrderID());
            pstmt.setInt(2, orderPizza.getPizzaID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("OrderPizza: " + orderPizza.toString() + " deleted");
            }
        } catch (SQLException e) {
            System.out.println("Deletion Error: " + e.getMessage());
        }
    }

    @Override
    public void update(OrderPizza orderPizza) {
        String query = "UPDATE orderPizza SET quantity = ? WHERE orderID = ? AND pizzaID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderPizza.getQuantity());
            pstmt.setInt(2, orderPizza.getOrderID());
            pstmt.setInt(3, orderPizza.getPizzaID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("OrderPizza: " + orderPizza.toString() + " updated");
            }
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }

    @Override
    public List<OrderPizza> findByOrderID(int orderID) {
        String query = "SELECT * FROM orderPizza WHERE orderID = ?";
        List<OrderPizza> orderPizzaList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OrderPizza orderPizza = new OrderPizza(
                        rs.getInt("orderID"),
                        rs.getInt("pizzaID"),
                        rs.getInt("quantity")
                    );
                    orderPizzaList.add(orderPizza);
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return orderPizzaList;
    }

    @Override
    public OrderPizza findByOrderIDAndPizzaID(int orderID, int pizzaID) {
        String query = "SELECT * FROM orderPizza WHERE orderID = ? AND pizzaID = ?";
        OrderPizza orderPizza = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderID);
            pstmt.setInt(2, pizzaID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    orderPizza = new OrderPizza(
                        rs.getInt("orderID"),
                        rs.getInt("pizzaID"),
                        rs.getInt("quantity")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return orderPizza;
    }

    @Override
    public List<Pizza> listPizzas(int orderID) {
        String query = "SELECT pizza.ID, pizza.name " +
                    "FROM pizza " +
                    "JOIN orderPizza ON pizza.ID = orderPizza.pizzaID " +
                    "WHERE orderPizza.orderID = ?";
        List<Pizza> pizzas = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pizza pizza = new Pizza(rs.getString("name"));
                    pizza.setID(rs.getInt("ID"));
                    pizzas.add(pizza);
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return pizzas;
    }
}
