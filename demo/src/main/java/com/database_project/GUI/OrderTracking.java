package com.database_project.GUI;

import javax.swing.*;

import com.database_project.DAO.OrderDAOImpl;
import com.database_project.config.DatabaseConfig;
import com.database_project.entity.Order;
import com.database_project.service.OrderService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderTracking extends JFrame {
    private int customerId;

    public OrderTracking(int customerId, Connection conn) {
        this.customerId = customerId;
        OrderDAOImpl orderDAOImpl = new OrderDAOImpl(conn);
        int orderID = orderDAOImpl.findOrderIDbyCustomerID(customerId);
        

        setTitle("Order Tracking");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create buttons
        JButton trackOrderButton = new JButton("Track your order");

        trackOrderButton.addActionListener(e -> {
            // Open a new connection to the database
            try (Connection conn2 = DatabaseConfig.getConnection()) {
                // Instantiate the OrderService with the new connection
                OrderDAOImpl orderDAOImpl2 = new OrderDAOImpl(conn2);
                Order order = orderDAOImpl2.findByID(orderID); 
                
        
                // Get the order status using the order ID
                String orderStatus = order.getStatus();
        
                // Display the order status in a message dialog
                if (orderStatus != null) {
                    JOptionPane.showMessageDialog(OrderTracking.this, "Order Status: " + orderStatus, "Order Tracking", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(OrderTracking.this, "Order not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
        
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(OrderTracking.this, "Database error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        JButton cancelOrderButton = new JButton("Cancel order");
        cancelOrderButton.addActionListener(e -> {
            try (Connection conn2 = DatabaseConfig.getConnection()) {
                OrderService orderService = new OrderService(conn2);
                OrderDAOImpl orderDAOImpl2 = new OrderDAOImpl(conn2);
            
                orderService.cancelOrder(orderID);
                boolean isCanceled = orderService.cancelOrder(orderID);

                if (isCanceled) {
                    JOptionPane.showMessageDialog(OrderTracking.this, "Order canceled.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // If the order is not canceled, check the reason
                    Order order = orderDAOImpl2.findByID(orderID); 
                    System.out.println(orderID);
                    if (order == null) {
                        JOptionPane.showMessageDialog(OrderTracking.this, "Order not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(OrderTracking.this, "Order cannot be canceled after 5 minutes.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                

                }


            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        
    
    

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(trackOrderButton);
        panel.add(cancelOrderButton);

        add(panel);
        setVisible(true);
    }

    
}

