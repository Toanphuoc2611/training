package com.demo.dao;

import java.util.List;
import com.demo.model.Customer;
import java.util.Date;

/**
 * DAO Interface cho Customer
 * Implementation sử dụng SessionFactory + Named Queries (không dùng HibernateTemplate)
 */
public interface CustomerDao {
    Customer findById(int id);
    List<Customer> search(String name);
    List<Customer> searchByCondition(String name, String gender, Date fromDate, Date toDate);
    List<Customer> findAll();
    void save(Customer customer);
    void update(Customer customer);
    void delete(Customer customer);
    void deleteById(int id);
    Customer login(String user, String pass);
    int count();
}
