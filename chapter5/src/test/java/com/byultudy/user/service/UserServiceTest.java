package com.byultudy.user.service;


import com.byultudy.user.dao.UserDao;
import com.byultudy.user.domain.Level;
import com.byultudy.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    private UserDao userDao;

    List<User> users;
    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("test1", "테스터1", "test", Level.BASIC, 49 ,0),
                new User("test2", "테스터2", "test", Level.BASIC, 50 ,0),
                new User("test3", "테스터3", "test", Level.SILVER, 60 ,29),
                new User("test4", "테스터4", "test", Level.SILVER, 60 ,30),
                new User("test5", "테스터5", "test", Level.GOLD, 100 ,100)
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user: users) {
            userDao.add(user);
        }
        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);

        assertThat(this.userService, is(notNullValue()));
    }

    private void checkLevel(final User user, final Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }
}