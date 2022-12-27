package com.byultudy.user.service;

import com.byultudy.user.sqlService.UpdatableSqlRegistry;
import com.byultudy.user.sqlService.updatable.ConcurrentHashMapSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }
}
