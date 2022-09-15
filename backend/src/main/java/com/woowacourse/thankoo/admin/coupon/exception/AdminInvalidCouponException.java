package com.woowacourse.thankoo.admin.coupon.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidCouponException extends AdminBadRequestException {

    public AdminInvalidCouponException(final AdminErrorType errorType) {
        super(errorType);
    }
}
