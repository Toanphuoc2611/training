package com.demo.daoImpl;

import java.util.List;

import com.demo.dao.CustomerDao;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import com.demo.model.Customer;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomerDaoImpl implements CustomerDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Customer findById(int id) {
        return (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
    }

    @Override
    public List<Customer> search(String name) {
        String hql = "FROM Customer c WHERE c.name LIKE :name";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", "%" + name + "%");
        return query.list();
    }

    @Override
    public Customer login(String user, String pass) {
        String hql = "FROM Customer c WHERE c.username = :u AND c.password = :p";
        Query q = sessionFactory.getCurrentSession().createQuery(hql);
        q.setString("u", user);
        q.setString("p", pass);
        List<Customer> list = q.list();
        return list.isEmpty() ? null : list.get(0);
    }
}
