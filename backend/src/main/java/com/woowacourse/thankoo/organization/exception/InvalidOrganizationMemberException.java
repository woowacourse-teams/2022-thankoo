package com.woowacourse.thankoo.organization.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidOrganizationMemberException extends BadRequestException {

    public InvalidOrganizationMemberException(final ErrorType errorType) {
        super(errorType);
    }
}
