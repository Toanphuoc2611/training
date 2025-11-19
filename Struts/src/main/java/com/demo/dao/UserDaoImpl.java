package com.demo.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Query;
import com.demo.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User login(String userId, String password) {
        String hql = "FROM User u WHERE u.userId = :uid AND u.password = :pass AND u.deleted IS NULL";
        Query q = sessionFactory.getCurrentSession().createQuery(hql);
        q.setString("uid", userId);
        q.setString("pass", password);

        return (User) q.uniqueResult(); // trả về null nếu không tồn tại
    }

}
