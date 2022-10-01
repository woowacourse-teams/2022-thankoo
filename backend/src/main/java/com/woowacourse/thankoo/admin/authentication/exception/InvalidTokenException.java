package com.woowacourse.thankoo.admin.authentication.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.exception.UnAuthenticationAdminException;

public class InvalidTokenException extends UnAuthenticationAdminException {

    public InvalidTokenException(final AdminErrorType adminErrorType) {
        super(adminErrorType);
    }
}
