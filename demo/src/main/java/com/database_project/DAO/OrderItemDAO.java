package com.database_project.DAO;

import com.database_project.entity.OrderItem;

public interface OrderItemDAO {
    void insert(OrderItem orderItem);
    void delete(OrderItem orderItem);
    void update(OrderItem orderItem);
    OrderItem findById(int id);
}
