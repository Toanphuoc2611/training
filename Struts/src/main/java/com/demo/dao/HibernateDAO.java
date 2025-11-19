package com.demo.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.util.List;

public class HibernateDAO<T> implements Serializable {
    
    protected SessionFactory sessionFactory;
    protected Class<T> entityClass;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity) {
        getSession().save(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public void deleteById(Serializable id, Class<T> clazz) {
        T entity = (T) getSession().get(clazz, id);
        if (entity != null) {
            getSession().delete(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public T getById(Serializable id, Class<T> clazz) {
        return (T) getSession().get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(Class<T> clazz) {
        Query query = getSession().createQuery("from " + clazz.getName());
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByHql(String hql) {
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByHql(String hql, Object... params) {
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        return query.list();
    }

    public int countAll(Class<T> clazz) {
        Query query = getSession().createQuery("select count(*) from " + clazz.getName());
        Long count = (Long) query.uniqueResult();
        return count != null ? count.intValue() : 0;
    }
}
