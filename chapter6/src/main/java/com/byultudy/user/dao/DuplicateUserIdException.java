package com.byultudy.user.dao;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(final Throwable cause) {
        super(cause);
    }
}
