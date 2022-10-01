package com.woowacourse.thankoo.admin.common.exception;

public class UnauthenticationAdminException extends RuntimeException {

    public UnauthenticationAdminException(final AdminErrorType adminErrorType) {
        super(adminErrorType.getMessage());
    }
}
