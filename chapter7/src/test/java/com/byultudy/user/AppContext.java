package com.byultudy.user;

import com.byultudy.user.service.DummyMailSender;
import com.byultudy.user.service.UserService;
import com.byultudy.user.service.UserServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.byultudy.user")
@Import(SqlServiceContext.class)
@PropertySource("/database.properties")
public class AppContext {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();

        try {
            ds.setDriverClass((Class<? extends java.sql.Driver>)Class.forName(env.getProperty("db.driverClass")));
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ds.setUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.username"));
        ds.setPassword(env.getProperty("db.password"));

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    @Configuration
    @Profile("production")
    public static class ProductionAppContext {
        @Bean
        public MailSender mailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("localhost");
            return mailSender;
        }
    }

    @Configuration
    @Profile("test")
    public static class TestAppContext {
        @Bean
        public UserService testUserService() {
            return new UserServiceTest.TestUserService();
        }

        @Bean
        public MailSender mailSender() {
            return new DummyMailSender();
        }
    }
}