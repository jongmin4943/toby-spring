package com.byultudy.user.dao;

import com.byultudy.user.domain.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDao {

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        return user;
    };
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) throws DuplicateUserIdException {
        // 291 page
        try {
            this.jdbcTemplate.update(
                    "insert into users(id, name, password) values (?,?,?)",
                    user.getId(),
                    user.getName(),
                    user.getPassword()
            );
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserIdException(e);
        }

    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, userRowMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", userRowMapper);
    }
}
