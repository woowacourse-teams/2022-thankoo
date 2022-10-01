package com.woowacourse.thankoo.admin.common.exception;

public class UnAuthenticationAdminException extends RuntimeException {

    public UnAuthenticationAdminException(final AdminErrorType adminErrorType) {
        super(adminErrorType.getMessage());
    }
}
