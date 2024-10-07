package com.database_project.DAO;

import java.util.List;

import com.database_project.entity.Ingredient;
import com.database_project.entity.Pizza;

public interface PizzaDAO {
    void insert(Pizza item);
    void delete(Pizza item);
    void update(Pizza item);
    Pizza findByName(String name);
    Pizza findByID(int id);
    List<Ingredient> listIngredients(int pizzaID);
}
