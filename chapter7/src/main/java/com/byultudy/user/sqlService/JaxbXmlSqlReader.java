package com.byultudy.user.sqlService;

import com.byultudy.user.sqlService.jaxb.SqlType;
import com.byultudy.user.sqlService.jaxb.Sqlmap;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class JaxbXmlSqlReader implements SqlReader{
    private static final String DEFAULT_SQLMAP_FILE = "/sqlmap.xml";
    private String sqlmapFile = DEFAULT_SQLMAP_FILE;
    public void setSqlmapFile(final String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
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
