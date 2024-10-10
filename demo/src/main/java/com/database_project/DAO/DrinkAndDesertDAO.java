package com.database_project.DAO;

import com.database_project.entity.DrinkAndDesert;

public interface  DrinkAndDesertDAO {
    void insert(DrinkAndDesert item);
    void delete(DrinkAndDesert item);
    void update(DrinkAndDesert item);
    DrinkAndDesert findByName(String name);
    DrinkAndDesert findByID(int id);

}
