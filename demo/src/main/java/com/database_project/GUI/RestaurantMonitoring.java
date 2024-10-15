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
    private OrderService orderService; 

    public RestaurantMonitoring(Connection conn, OrderService orderService) {
        this.orderService = orderService; 
        setTitle("Restaurant Monitoring");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

    
        tableModel = new DefaultTableModel(new String[]{"Pizzas to be prepared: "}, 0);
        pizzaTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pizzaTable);

        add(scrollPane, BorderLayout.CENTER);

        loadPizzas();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadPizzas() {
        try {
            List<Pizza> todoPizzas = orderService.getToDoPizzas(); // retrieve the pizzas

            for (Pizza pizza : todoPizzas) {
                tableModel.addRow(new Object[]{pizza.getName()}); // add only the pizza name
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Error loading pizzas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    
}


