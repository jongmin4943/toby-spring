package com.byultudy.user.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

public class UserTest {
    User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level: levels) {
            if(level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel(), is(level.nextLevel()));
        }
    }
    @Test
    public void cannotUpgradeLevel() {
        Level[] levels = Level.values();
        assertThrows(IllegalArgumentException.class ,()->{
            for (Level level: levels) {
                if(level.nextLevel() != null) continue;
                user.setLevel(level);
                user.upgradeLevel();
            }
        });
    }
}