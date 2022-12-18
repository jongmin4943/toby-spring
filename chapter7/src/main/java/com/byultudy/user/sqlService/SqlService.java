package com.byultudy.user.sqlService;

public interface SqlService {
    String getSql(String kye) throws SqlRetrievalFailureException;
}
