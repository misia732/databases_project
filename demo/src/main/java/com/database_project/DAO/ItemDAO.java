package com.database_project.DAO;

import com.database_project.entity.Item;

public interface ItemDAO {
    void insert(Item item);
    void delete(Item item);
    void update(Item item);
    Item findByName(String name);
}
