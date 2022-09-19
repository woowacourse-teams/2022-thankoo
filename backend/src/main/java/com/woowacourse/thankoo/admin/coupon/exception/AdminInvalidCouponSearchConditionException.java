package com.woowacourse.thankoo.admin.coupon.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidCouponSearchConditionException extends AdminBadRequestException {

    public AdminInvalidCouponSearchConditionException(final AdminErrorType errorType) {
        super(errorType);
    }
}
