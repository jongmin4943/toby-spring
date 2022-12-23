package com.byultudy.user.sqlService;

public class SqlRetrievalFailureException extends RuntimeException{
    public SqlRetrievalFailureException(final String message) {
        super(message);
    }

    public SqlRetrievalFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SqlRetrievalFailureException(final Throwable cause) {
        super(cause);
    }
}
