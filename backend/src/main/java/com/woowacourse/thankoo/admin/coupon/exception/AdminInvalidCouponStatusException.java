package com.woowacourse.thankoo.admin.coupon.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidCouponStatusException extends AdminBadRequestException {

    public AdminInvalidCouponStatusException(final AdminErrorType errorType) {
        super(errorType);
    }
}
