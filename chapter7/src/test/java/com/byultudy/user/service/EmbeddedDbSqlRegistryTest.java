package com.byultudy.user.service;

import com.byultudy.user.sqlService.SqlUpdateFailureException;
import com.byultudy.user.sqlService.UpdatableSqlRegistry;
import com.byultudy.user.sqlService.updatable.EmbeddedDbSqlRegistry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.HashMap;
import java.util.Map;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void transactionalUpdate() {
        checkFind("SQL1", "SQL2", "SQL3");

        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY9999!@#$", "Modified9999");

        try {
            sqlRegistry.updateSql(sqlmap);
            Assert.fail();
        }
        catch(SqlUpdateFailureException e) {}

        checkFind("SQL1", "SQL2", "SQL3");
    }

}