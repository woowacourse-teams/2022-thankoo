package com.woowacourse.thankoo.admin.common.exception;

public class AdminUnAuthenticationException extends RuntimeException {

    public AdminUnAuthenticationException(final AdminErrorType adminErrorType) {
        super(adminErrorType.getMessage());
    }
}
