package com.byultudy.user.service;

import com.byultudy.user.domain.User;

import java.util.List;

public interface UserService {
    void upgradeLevels();

    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    void update(User user);
}
