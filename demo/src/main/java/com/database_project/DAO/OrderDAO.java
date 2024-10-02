package com.database_project.DAO;

import com.database_project.entity.Order;

public interface OrderDAO {
    void insert(Order order);
    void delete(Order order);
    void update(Order order);
    Order findById(int id);
}
