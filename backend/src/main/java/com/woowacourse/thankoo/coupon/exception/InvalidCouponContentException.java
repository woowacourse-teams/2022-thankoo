package com.woowacourse.thankoo.coupon.exception;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public class InvalidCouponContentException extends BadRequestException {

    public InvalidCouponContentException(final ErrorType errorType) {
        super(errorType);
    }
}
