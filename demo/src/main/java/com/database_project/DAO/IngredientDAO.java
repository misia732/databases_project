package com.database_project.DAO;

import com.database_project.entity.Ingredient;

public interface IngredientDAO {
    void insert(Ingredient ingredient);
    void delete(Ingredient ingredient);
    void update(Ingredient ingredient);
    Ingredient findByName(String name);
}
