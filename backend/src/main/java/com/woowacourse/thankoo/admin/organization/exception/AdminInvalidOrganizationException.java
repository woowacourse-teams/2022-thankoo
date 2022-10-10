package com.woowacourse.thankoo.admin.organization.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidOrganizationException extends RuntimeException {

    public AdminInvalidOrganizationException(final AdminErrorType errorType) {
        super(errorType.getMessage());
    }
}
