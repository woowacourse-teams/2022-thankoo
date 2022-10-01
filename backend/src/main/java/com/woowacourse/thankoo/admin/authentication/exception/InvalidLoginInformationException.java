package com.woowacourse.thankoo.admin.authentication.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.exception.UnAuthenticationAdminException;

public class InvalidLoginInformationException extends UnAuthenticationAdminException {

    public InvalidLoginInformationException(final AdminErrorType adminErrorType) {
        super(adminErrorType);
    }
}
