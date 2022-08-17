package com.woowacourse.thankoo.heart.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidHeartException extends BadRequestException {

    public InvalidHeartException(final ErrorType errorType) {
        super(errorType);
    }
}
