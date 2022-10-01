package com.woowacourse.thankoo.admin.authentication.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.exception.UnauthenticationAdminException;

public class InvalidAdminAuthenticationException extends UnauthenticationAdminException {

    public InvalidAdminAuthenticationException(final AdminErrorType adminErrorType) {
        super(adminErrorType);
    }
}
