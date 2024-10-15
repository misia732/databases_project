package com.database_project.GUI;

import javax.swing.*;

import com.database_project.config.DatabaseConfig;
import com.database_project.service.OrderService;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Connection;



public class AdminScreen extends JFrame {

    public AdminScreen() {
        
        super("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200); 

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton monitoringButton = new JButton("Restaurant Monitoring");
        JButton earningsReportButton = new JButton("Earnings Report");

        earningsReportButton.addActionListener(e -> {
            try (Connection conn = DatabaseConfig.getConnection()) {
                new EarningsReport(conn); 
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        monitoringButton.addActionListener(e -> {
            try (Connection conn = DatabaseConfig.getConnection()) {
                
                OrderService orderService = new OrderService(conn);
                
                
                new RestaurantMonitoring(conn, orderService);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        
        panel.add(monitoringButton);
        panel.add(earningsReportButton);

        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        setVisible(true);
    }


}
