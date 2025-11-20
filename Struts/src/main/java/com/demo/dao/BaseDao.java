package com.demo.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseDao<T> {
    
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Query createQuery(String hql) {
        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        session.beginTransaction().commit();
        return query;
    }
}
