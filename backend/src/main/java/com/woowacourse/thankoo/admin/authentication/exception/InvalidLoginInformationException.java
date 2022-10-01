package com.woowacourse.thankoo.admin.authentication.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.exception.UnauthenticationAdminException;

public class InvalidLoginInformationException extends UnauthenticationAdminException {

    public InvalidLoginInformationException(final AdminErrorType adminErrorType) {
        super(adminErrorType);
    }
}
