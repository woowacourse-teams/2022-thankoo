package com.woowacourse.thankoo.auth.exception;

public class InvalidAuthenticationException extends RuntimeException {

    public InvalidAuthenticationException(final String message) {
        super(message);
    }
}
