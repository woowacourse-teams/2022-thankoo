package com.woowacourse.thankoo.authentication.exception;

public class InvalidAuthenticationException extends RuntimeException {

    public InvalidAuthenticationException(final String message) {
        super(message);
    }
}
