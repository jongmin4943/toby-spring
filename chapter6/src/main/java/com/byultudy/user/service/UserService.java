package com.byultudy.user.service;

import com.byultudy.user.domain.User;

public interface UserService {
    void upgradeLevels();

    void add(User user);
}
