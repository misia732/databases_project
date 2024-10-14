package com.database_project.GUI;

import javax.swing.*;


import com.database_project.DAO.OrderDAOImpl;
import com.database_project.DAO.OrderPizzaDAOImpl;
import com.database_project.DAO.PizzaDAOImpl;
import com.database_project.config.DatabaseConfig;
import com.database_project.entity.Order;
import com.database_project.entity.OrderPizza;
import com.database_project.entity.Pizza;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

import com.database_project.DAO.CustomerDAOImpl;
import java.sql.Connection;
import java.time.*;


public class AdminScreen extends JFrame {

    public AdminScreen() {
        // Set the title and default close operation for the JFrame
        super("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200); // Set the size of the window

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column, with gaps

        // Create buttons
        JButton monitoringButton = new JButton("Restaurant Monitoring");
        JButton earningsReportButton = new JButton("Earnings Report");

        earningsReportButton.addActionListener(e -> {
            try (Connection conn = DatabaseConfig.getConnection()) {
                new EarningsReport(conn); // Open the Earnings Report screen
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        monitoringButton.addActionListener(e -> {
            try (Connection conn = DatabaseConfig.getConnection()) {
                new RestaurantMonitoring(conn); // Open the Restaurant Monitoring screen
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        // Add buttons to the panel
        panel.add(monitoringButton);
        panel.add(earningsReportButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Make the frame visible
        setVisible(true);
    }


}
