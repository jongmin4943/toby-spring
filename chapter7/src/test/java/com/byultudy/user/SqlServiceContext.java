package com.byultudy.user;

import com.byultudy.user.sqlService.OxmSqlService;
import com.byultudy.user.sqlService.SqlRegistry;
import com.byultudy.user.sqlService.SqlService;
import com.byultudy.user.sqlService.updatable.EmbeddedDbSqlRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;

@Configuration
public class SqlServiceContext {
    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());
        return sqlService;
    }

    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());
        return sqlRegistry;
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("springbook.user.sqlservice.jaxb");
        return marshaller;
    }

    @Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
                .build();
    }
}
