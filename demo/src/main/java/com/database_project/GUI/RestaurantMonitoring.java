package com.database_project.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.database_project.entity.Pizza;
import com.database_project.service.OrderService;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RestaurantMonitoring extends JFrame {
    private JTable pizzaTable;
    private DefaultTableModel tableModel;
    private OrderService orderService; // Now we'll initialize this

    // Updated constructor to accept an OrderService instance
    public RestaurantMonitoring(Connection conn, OrderService orderService) {
        this.orderService = orderService; // Initialize the orderService
        setTitle("Restaurant Monitoring");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create table model with only Pizza Name
        tableModel = new DefaultTableModel(new String[]{"Pizzas to be prepared: "}, 0);
        pizzaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pizzaTable);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Load and display pizzas
        loadPizzas();

        // Center the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadPizzas() {
        try {
            List<Pizza> todoPizzas = orderService.getToDoPizzas(); // Retrieve the pizzas
            // Populate the table model with just the pizza names
            for (Pizza pizza : todoPizzas) {
                tableModel.addRow(new Object[]{pizza.getName()}); // Add only the pizza name
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            JOptionPane.showMessageDialog(this, "Error loading pizzas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    
}


