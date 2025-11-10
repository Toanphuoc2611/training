package com.demo.dao;

public class UserDAO {

    // Giả lập kiểm tra login (sau này có thể dùng JDBC thật)
    public boolean checkLogin(String username, String password) {
        return "admin".equals(username) && "123".equals(password);
    }
}
