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

import static com.byultudy.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.byultudy.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;
import static org.hamcrest.CoreMatchers.is;
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
                new User("test1", "테스터1", "test", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER -1 , 0),
                new User("test2", "테스터2", "test", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER ,0),
                new User("test3", "테스터3", "test", Level.SILVER, 60 ,MIN_RECOMMEND_FOR_GOLD-1),
                new User("test4", "테스터4", "test", Level.SILVER, 60 ,MIN_RECOMMEND_FOR_GOLD),
                new User("test5", "테스터5", "test", Level.GOLD, 100 ,Integer.MAX_VALUE)
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user: users) {
            userDao.add(user);
        }
        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4); // GOLD
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }

    private void checkLevel(final User user, final boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if(upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }
}