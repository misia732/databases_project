package com.database_project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.database_project.DAO.DeliveryPersonnelDAO;
import com.database_project.DAO.DeliveryPersonnelDAOImpl;
import com.database_project.DAO.OrderDAO;
import com.database_project.DAO.OrderDAOImpl;
import com.database_project.entity.DeliveryPersonnel;
import com.database_project.entity.Order;

public class DeliveryService {

    private Connection conn;
    private DeliveryPersonnelDAO deliveryPersonnelDAO;
    private OrderDAO orderDAO;

    public DeliveryService(Connection conn) {
        this.conn = conn;
        this.deliveryPersonnelDAO = new DeliveryPersonnelDAOImpl(conn);
        this.orderDAO = new OrderDAOImpl(conn);
    }

    public void updateOrderStatus(Order order, String status) throws SQLException {
        // check if order exists
        if (orderDAO.findById(order.getID()) == null) {
            System.out.println("Order not found.");
            return;
        }
        
        order.setStatus(status);
        orderDAO.update(order);
    }

    public void assignDeliveryPersonnel(Order order) throws SQLException {
        DeliveryPersonnel availablePersonnel = deliveryPersonnelDAO.findAvailablePersonnel();
        
        if (availablePersonnel == null) {
            System.out.println("No delivery personnel available.");
            return;
        }

        order.setDeliveryPersonnelID(availablePersonnel.getID());
        order.setStatus("out for delivery");
        orderDAO.update(order);

        availablePersonnel.setStatus("busy");
        deliveryPersonnelDAO.update(availablePersonnel);
    }

    public LocalDateTime updateDeliveryTime(int orderId) throws SQLException {
        Order order = orderDAO.findById(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deliveryTime = now.plusMinutes(30);

        order.setDeliveryTime(deliveryTime);
        orderDAO.update(order);
        
        return deliveryTime;
    }
}
