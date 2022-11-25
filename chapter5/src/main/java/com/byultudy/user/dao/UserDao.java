package com.byultudy.user.dao;

import com.byultudy.user.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user) throws DuplicateUserIdException;

    User get(String id);

    void deleteAll();

    int getCount();

    List<User> getAll();
}
