package com.shengyu.oj.service;

import com.shengyu.oj.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
