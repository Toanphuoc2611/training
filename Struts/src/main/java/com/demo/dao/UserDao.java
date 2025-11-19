package com.demo.dao;

import com.demo.model.User;

public interface UserDao {
    User login(String username, String password);
}
