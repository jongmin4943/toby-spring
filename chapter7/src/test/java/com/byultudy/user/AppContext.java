package com.byultudy.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.byultudy.user")
@Import(SqlServiceContext.class)
public class AppContext {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(Driver.class);
        ds.setUrl("jdbc:mariadb://localhost/tobi");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }
}
