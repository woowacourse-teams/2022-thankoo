package com.woowacourse.thankoo.alarm.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidAlarmTypeException extends BadRequestException {

    public InvalidAlarmTypeException(final ErrorType errorType) {
        super(errorType);
    }
}
