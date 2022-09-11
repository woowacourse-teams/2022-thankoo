package com.woowacourse.thankoo.admin.coupon.domain;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.coupon.exception.AdminInvalidCouponStatusException;
import java.util.Arrays;

public enum AdminCouponStatus {

    ALL,
    NOT_USED,
    RESERVING,
    RESERVED,
    USED,
    EXPIRED;

    public static AdminCouponStatus of(final String name) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new AdminInvalidCouponStatusException(AdminErrorType.INVALID_COUPON_STATUS));
    }

    public boolean isAll() {
        return this == ALL;
    }
}
