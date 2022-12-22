package com.byultudy.user.sqlService;

public class SqlNotFoundException extends RuntimeException{
    public SqlNotFoundException(final String message) {
        super(message);
    }

    public SqlNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
