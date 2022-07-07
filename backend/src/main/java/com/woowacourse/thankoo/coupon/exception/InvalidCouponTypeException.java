package com.woowacourse.thankoo.coupon.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidCouponTypeException extends BadRequestException {

    public InvalidCouponTypeException(final ErrorType errorType) {
        super(errorType);
    }
}
