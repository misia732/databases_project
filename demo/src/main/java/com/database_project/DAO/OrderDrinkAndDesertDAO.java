package com.database_project.DAO;

import java.util.List;

import com.database_project.entity.OrderDrinkAndDesert;

public interface OrderDrinkAndDesertDAO {
    void insert(OrderDrinkAndDesert orderDrinkAndDesert);
    void delete(OrderDrinkAndDesert orderDrinkAndDesert);
    void update(OrderDrinkAndDesert orderDrinkAndDesert);
    List<OrderDrinkAndDesert> findByOrderID(int orderID);
    OrderDrinkAndDesert findByOrderIDAndDrinkAndDesertID(int orderID, int drinkAndDesertID);
}
