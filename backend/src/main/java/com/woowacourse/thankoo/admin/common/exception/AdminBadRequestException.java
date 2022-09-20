package com.woowacourse.thankoo.admin.common.exception;

public class AdminBadRequestException extends RuntimeException {

    public AdminBadRequestException(final AdminErrorType errorType) {
        super(errorType.getMessage());
    }
}
