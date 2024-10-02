package com.database_project.DAO;

import com.database_project.entity.DeliveryPersonnel;

public interface DeliveryPersonnelDAO {
    void insert(DeliveryPersonnel deliveryPersonnel);
    void delete(DeliveryPersonnel deliveryPersonnel);
    void update(DeliveryPersonnel deliveryPersonnel);
    DeliveryPersonnel findByID(int id);
    
}
