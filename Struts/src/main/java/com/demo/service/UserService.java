package com.demo.service;

import com.demo.model.User;

public interface UserService {
    User login(String username, String password);
}
