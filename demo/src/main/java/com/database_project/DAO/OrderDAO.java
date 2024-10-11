package com.database_project.DAO;

import com.database_project.entity.Order;
import com.database_project.entity.Pizza;
import java.util.List;
import java.time.LocalDateTime;


public interface OrderDAO {
    void insert(Order order);
    void delete(Order order);
    void update(Order order);
    Order findById(int id);
    public List<Order> findOrdersByStatus(String status);
    public List<Order> findOrdersByPostalcodeAndTime(String postalCode, LocalDateTime timeWindow);
}
