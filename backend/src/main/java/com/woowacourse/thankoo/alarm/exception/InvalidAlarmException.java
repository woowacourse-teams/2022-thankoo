package com.woowacourse.thankoo.alarm.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidAlarmException extends BadRequestException {

    public InvalidAlarmException(final ErrorType errorType) {
        super(errorType);
    }
}
