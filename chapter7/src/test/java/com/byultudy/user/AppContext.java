package com.byultudy.user;

import com.byultudy.user.service.DummyMailSender;
import com.byultudy.user.service.UserService;
import com.byultudy.user.service.UserServiceTest;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
        ds.setUrl("jdbc:mysql://localhost/springbook?characterEncoding=UTF-8");
        ds.setUsername("spring");
        ds.setPassword("book");
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