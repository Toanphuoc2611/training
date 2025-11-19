package com.demo.dao;

import java.util.List;
import com.demo.model.Customer;

public interface CustomerDao {
    Customer findById(int id);
    List<Customer> search(String name);
    Customer login(String user, String pass);
}
