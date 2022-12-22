package com.byultudy.user.sqlService;

public interface SqlRegistry {
    void register(String key, String sql);

    String findSql(String key) throws SqlNotFoundException;
}
