package com.database_project.DAO;

import com.database_project.entity.DiscountCode;

public interface DiscountCodeDAO {
    void insert(DiscountCode discountCode);
    void delete(DiscountCode discountCode);
    void update(DiscountCode discountCode);
    DiscountCode findByID(String id);
}
