package com.woowacourse.thankoo.authentication.exception;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.exception.UnauthorizedException;

public class InvalidAuthenticationException extends UnauthorizedException {

    public InvalidAuthenticationException(final ErrorType errorType) {
        super(errorType);
    }
}
