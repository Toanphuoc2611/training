package com.demo.service;

import com.demo.dao.UserDao;
import com.demo.model.User;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }
}
