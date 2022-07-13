package com.woowacourse.thankoo.coupon.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidCouponStatusException extends BadRequestException {

    public InvalidCouponStatusException(final ErrorType errorType) {
        super(errorType);
    }
}