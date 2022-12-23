package com.byultudy.user.sqlService;

import com.byultudy.user.sqlService.jaxb.SqlType;
import com.byultudy.user.sqlService.jaxb.Sqlmap;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlSqlService implements SqlService, SqlRegistry, SqlReader {
    private final Map<String, String> sqlMap = new HashMap<>();
    private SqlReader sqlReader;
    private SqlRegistry sqlRegistry;
    private String sqlmapFile;

    public void setSqlmapFile(final String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
    }

    public void setSqlReader(final SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(final SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    @PostConstruct
    private void loadSql() {
        this.sqlReader.read(this.sqlRegistry);
    }

    @Override
    public String getSql(final String key) throws SqlRetrievalFailureException {
        try {
            return this.sqlRegistry.findSql(key);
        } catch (SqlNotFoundException e) {
            throw new SqlRetrievalFailureException(e);
        }
    }

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

    @Override
    public void read(final SqlRegistry sqlRegistry) {
        String contextPath = Sqlmap.class.getPackage().getName();
        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = getClass().getResourceAsStream(this.sqlmapFile);
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

            for (SqlType sql : sqlmap.getSql()) {
                sqlRegistry.register(sql.getKey(), sql.getValue());
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
