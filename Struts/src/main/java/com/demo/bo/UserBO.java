package com.demo.bo;

import com.demo.dao.UserDAO;

public class UserBO {
    private UserDAO dao = new UserDAO();

    public boolean checkLogin(String username, String password) {
        return dao.checkLogin(username, password);
    }
}
