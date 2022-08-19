package com.woowacourse.thankoo.coupon.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidCouponException extends BadRequestException {

    public InvalidCouponException(final ErrorType errorType) {
        super(errorType);
    }
}
