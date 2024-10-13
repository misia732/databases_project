package com.database_project.DAO;

import com.database_project.entity.Customer;

public interface CustomerDAO {
    int insert(Customer customer);
    void delete(Customer customer);
    void update(Customer customer);
    Customer findByEmail(String email);
    Customer findByID(int id);

}