package com.byultudy.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = stmt.makePreparedStatement(c)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void executeSql(final String query) throws SQLException {
        this.workWithStatementStrategy((c)->c.prepareStatement(query));
    }
}
