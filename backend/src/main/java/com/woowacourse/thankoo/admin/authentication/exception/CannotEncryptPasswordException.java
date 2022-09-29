package com.woowacourse.thankoo.admin.authentication.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class CannotEncryptPasswordException extends AdminBadRequestException {
    public CannotEncryptPasswordException(final AdminErrorType errorType) {
        super(errorType);
    }
}
