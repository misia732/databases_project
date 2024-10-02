package com.database_project.DAO;

import com.database_project.entity.DeliveryPersonel;

public interface DeliveryPersonelDAO {
    void insert(DeliveryPersonel deliveryPersonel);
    void delete(DeliveryPersonel deliveryPersonel);
    void update(DeliveryPersonel deliveryPersonel);
    DeliveryPersonel findByID(int id);
    
}
