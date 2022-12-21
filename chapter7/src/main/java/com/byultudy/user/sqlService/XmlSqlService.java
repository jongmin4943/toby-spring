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

public class XmlSqlService implements SqlService{
    private final Map<String, String> sqlMap = new HashMap<>();
    private String sqlmapFile;

    public void setSqlmapFile(final String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
    }

    @PostConstruct
    private void loadSql() {
        String contextPath = Sqlmap.class.getPackage().getName();
        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = getClass().getResourceAsStream(this.sqlmapFile);
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

            for (SqlType sql: sqlmap.getSql()) {
                sqlMap.put(sql.getKey(), sql.getValue());
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSql(final String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);
        if(sql == null) {
            throw new SqlRetrievalFailureException(key + "를 이용해서 SQL 을 찾을 수 없습니다.");
        }
        return sql;
    }
}
