package com.byultudy.user.dao;

import com.byultudy.user.TestApplicationContext;
import com.byultudy.user.domain.Level;
import com.byultudy.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationContext.class)
public class UserDaoTest {
    @Autowired
    private UserDao dao;
    @Autowired
    private DataSource dataSource;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        this.user1 = new User("test1", "테스터1", "test", Level.BASIC, 1, 0, "test@test.com");
        this.user2 = new User("test2", "테스터2", "test", Level.SILVER, 55, 10, "test@test.com");
        this.user3 = new User("test3", "테스터3", "test", Level.GOLD, 100, 40, "test@test.com");
    }

    @Test
    public void addAndGet() {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        checkSameUser(userget1, user1);
        User userget2 = dao.get(user2.getId());
        checkSameUser(userget2, user2);
    }

    @Test
    public void getUserFailure() {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));
        assertThrows(EmptyResultDataAccessException.class, () ->
                dao.get("unknown")
        );
    }

    @Test
    public void count() {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));
        dao.add(user2);
        assertThat(dao.getCount(), is(2));
        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test
    public void getAll() {
        dao.deleteAll();
        List<User> users = dao.getAll();
        assertThat(users.size(), is(0));

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    @Test
    public void duplicationKey() {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));
        assertThrows(DataAccessException.class,
                () -> {
                    dao.add(user1);
                    dao.add(user1);
                }
        );
    }

    @Test
    public void sqlExceptionTranslate() {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DataIntegrityViolationException ex) {
            // MariaDB 는 현재 sql-error-codes.xml 에 정의가 안되어있어 DuplicateKeyException 불가능
            // 현 스프링 버전에서는 is 대신 instanceOf 로 검사
            SQLException sqlEx = (SQLException) ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            DataAccessException translate = set.translate(null, null, sqlEx);
            assertThat(translate, instanceOf(DataIntegrityViolationException.class));
        }
    }

    @Test
    public void update() {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);

        user1.setName("윤종민");
        user1.setPassword("spring");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        dao.update(user1);

        User user1updated = dao.get(user1.getId());
        checkSameUser(user1, user1updated);
        User user2updated = dao.get(user2.getId());
        checkSameUser(user2, user2updated);
    }

    private void checkSameUser(final User user1, final User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));
    }
}