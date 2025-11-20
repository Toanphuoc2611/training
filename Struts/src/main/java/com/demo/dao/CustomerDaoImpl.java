package com.demo.dao;

import java.util.List;

import com.demo.model.Customer;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao {

    @Override
    public Customer findById(int id) {
        return (Customer) getCurrentSession().get(Customer.class, id);
    }

    @Override
    public List<Customer> search(String name) {
        String hql = "FROM Customer c WHERE c.name LIKE :name";
        Query query = createQuery(hql);
        query.setString("name", "%" + name + "%");
        return query.list();
    }

    @Override
    public Customer login(String user, String pass) {
        String hql = "FROM Customer c WHERE c.username = :u AND c.password = :p";
        Query q = createQuery(hql);
        q.setString("u", user);
        q.setString("p", pass);
        List<Customer> list = q.list();
        return list.isEmpty() ? null : list.get(0);
    }
}
