package com.byultudy.user.sqlService;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry{
    private final Map<String, String> sqlMap = new HashMap<>();
    @Override
    public void register(final String key, final String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(final String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlNotFoundException(key + "를 이용해서 SQL 을 찾을 수 없습니다.");
        }
        return sql;
    }
}
