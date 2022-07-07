package com.woowacourse.thankoo.authentication.exception;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.exception.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {

    public InvalidTokenException(final ErrorType errorType) {
        super(errorType);
    }
}
