package com.byultudy.user.sqlService;

import java.util.Map;

public class SimpleSqlService implements SqlService {
    private Map<String, String> sqlMap;

    public void setSqlMap(final Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    @Override
    public String getSql(final String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);
        if(sql == null) {
            throw new SqlRetrievalFailureException(key + "에 대한 SQL 을 찾을 수 없습니다.");
        }
        return sql;
    }
}
