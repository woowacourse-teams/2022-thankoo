package com.woowacourse.thankoo.serial.exeption;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidCouponSerialException extends BadRequestException {

    public InvalidCouponSerialException(final ErrorType errorType) {
        super(errorType);
    }
}
