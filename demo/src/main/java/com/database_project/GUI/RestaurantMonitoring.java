package com.database_project.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantMonitoring extends JFrame {
    private JTable pizzaTable;
    private DefaultTableModel tableModel;

    public RestaurantMonitoring(Connection conn) {
        setTitle("Restaurant Monitoring");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create table model
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Pizza Name", "Quantity", "Order Date"}, 0);
        pizzaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pizzaTable);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Load pizzas that are not yet dispatched
        loadOrderedPizzas(conn);

        // Center the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadOrderedPizzas(Connection conn) {
        String query = "SELECT o.ID, p.name, op.quantity, o.placementTime " +
                       "FROM orderPizza AS op " +
                       "JOIN `order` AS o ON op.orderID = o.ID " +
                       "JOIN pizza AS p ON op.ID = p.ID " +
                       "WHERE o.status = 'being prepared'";


        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Clear existing rows
            tableModel.setRowCount(0);

            while (rs.next()) {
                // Add rows to the table
                tableModel.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getString("pizza_name"),
                        rs.getInt("quantity"),
                        rs.getDate("order_date")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching pizza orders: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
