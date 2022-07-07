package com.woowacourse.thankoo.common.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(final String message) {
        super(message);
    }
}
