package com.demo.dao;

import com.demo.model.User;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Override
    public User login(String userId, String password) {
        String hql = "FROM User u WHERE u.userId = :uid AND u.password = :pass AND u.deleted IS NULL";
        Query q = createQuery(hql);
        q.setString("uid", userId);
        q.setString("pass", password);
        return (User) q.uniqueResult();
    }

}
