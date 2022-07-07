package com.woowacourse.thankoo.member.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidMemberException extends BadRequestException {

    public InvalidMemberException(final ErrorType errorType) {
        super(errorType);
    }
}
