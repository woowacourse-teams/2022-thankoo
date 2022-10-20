package com.woowacourse.thankoo.admin.authentication.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.exception.AdminUnAuthenticationException;

public class InvalidTokenUnAuthenticationException extends AdminUnAuthenticationException {

    public InvalidTokenUnAuthenticationException(final AdminErrorType adminErrorType) {
        super(adminErrorType);
    }
}
