package com.database_project.DAO;

import java.util.List;

import com.database_project.entity.OrderPizza;
import com.database_project.entity.Pizza;

public interface OrderPizzaDAO {
    void insert(OrderPizza orderPizza);
    void delete(OrderPizza orderPizza);
    void update(OrderPizza orderPizza);
    List<OrderPizza> findByOrderID(int orderID);
    OrderPizza findByOrderIDAndPizzaID(int orderID, int pizzaID);
    List<Pizza> listPizzas(int orderID);
}
