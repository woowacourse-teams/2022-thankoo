package com.woowacourse.thankoo.organization.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidOrganizationException extends BadRequestException {

    public InvalidOrganizationException(final ErrorType errorType) {
        super(errorType);
    }
}
