package com.database_project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.database_project.DAO.*;
import com.database_project.entity.*;

public class DeliveryService {

    private Connection conn;
    private static DeliveryPersonnelDAO deliveryPersonnelDAO;
    private static OrderDAO orderDAO;
    private static OrderPizzaDAO orderPizzaDAO;
    private static CustomerDAO customerDAO;

    public DeliveryService(Connection conn) {
        this.conn = conn;
        this.deliveryPersonnelDAO = new DeliveryPersonnelDAOImpl(conn);
        this.orderDAO = new OrderDAOImpl(conn);
        this.orderPizzaDAO = new OrderPizzaDAOImpl(conn);
        this.customerDAO = new CustomerDAOImpl(conn);
    }

    public void updateOrderStatus(Order order, String status) throws SQLException {
        // check if order exists
        if (orderDAO.findByID(order.getID()) == null) {
            System.out.println("Order not found.");
            return;
        }
        
        order.setStatus(status);
        orderDAO.update(order);
    }

    public static int assignDeliveryPersonnel(Order order) throws SQLException {
        DeliveryPersonnel availablePersonnel = deliveryPersonnelDAO.findAvailablePersonnel();
        Customer customer = customerDAO.findByID(order.getCustomerID());
        if(!order.getStatus().equals("waiting for delivery")){
            System.out.println("Order is not waiting for delivery.");
            return -1;
        }

        if (availablePersonnel == null) {
            System.out.println("No delivery personnel available.");
            return -1;
        }

        order.setDeliveryPersonnelID(availablePersonnel.getID());
        order.setStatus("out for delivery");
        orderDAO.update(order);

        availablePersonnel.setStatus("busy");
        deliveryPersonnelDAO.update(availablePersonnel);

        
        List<Order> ordersForOneDelivery = new ArrayList<>();
        ordersForOneDelivery.add(order);
        if(numberOfPizzas(order) <= 3){
            List<Order> orderCluster = orderDAO.findOrdersByPostalcodeAndTime(customer.getPostalcode(), LocalDateTime.now().minusMinutes(3));
            for(Order orderToAdd : orderCluster){
                if (numberOfPizzas(orderToAdd) < 3) 
                    ordersForOneDelivery.add(orderToAdd);
            }
        }
        
        for(Order o : ordersForOneDelivery){
            o.setDeliveryPersonnelID(availablePersonnel.getID());
            o.setStatus("out for delivery");
            orderDAO.update(o);
            System.out.println("Order from customer " + customer.getID() + " will be delivered in 30 min.");
        }

        availablePersonnel.setStatus("busy");
        deliveryPersonnelDAO.update(availablePersonnel);
        return availablePersonnel.getID();
    }

    private static int numberOfPizzas(Order order){
        List<OrderPizza> orderPizzas = orderPizzaDAO.findByOrderID(order.getID());
        int numberOfPizzas = 0;
        for(OrderPizza orderPizza : orderPizzas){
            numberOfPizzas += orderPizza.getQuantity();
        }
        return numberOfPizzas;
    }
}
