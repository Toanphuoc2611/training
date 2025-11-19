package com.demo.service;

import java.util.List;
import com.demo.dao.CustomerDao;
import com.demo.model.Customer;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> search(String name) {
        return customerDao.search(name);
    }

    @Override
    public Customer login(String user, String pass) {
        return customerDao.login(user, pass);
    }
}
