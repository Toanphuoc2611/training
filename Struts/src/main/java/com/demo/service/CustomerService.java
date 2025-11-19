package com.demo.service;

import java.util.List;
import com.demo.model.Customer;

public interface CustomerService {
    Customer findById(int id);
    List<Customer> search(String name);
    Customer login(String user, String pass);
}
