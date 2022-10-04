package com.woowacourse.thankoo.admin.authentication.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.exception.AdminUnAuthenticationException;

public class InvalidLoginInformationUnAuthenticationException extends AdminUnAuthenticationException {

    public InvalidLoginInformationUnAuthenticationException(final AdminErrorType adminErrorType) {
        super(adminErrorType);
    }
}
