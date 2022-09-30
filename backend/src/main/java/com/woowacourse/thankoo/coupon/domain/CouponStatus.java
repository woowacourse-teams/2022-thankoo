package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponStatusException;
import java.util.Arrays;

public enum CouponStatus {

    NOT_USED,
    RESERVING,
    RESERVED,
    USED,
    EXPIRED;

    public static CouponStatus of(final String name) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new InvalidCouponStatusException(ErrorType.INVALID_COUPON_STATUS));
    }

    public boolean isNotUsed() {
        return this == NOT_USED;
    }

    public boolean isInReserveOrUsed() {
        return isReserving() || isReserved() || isUsed();
    }

    public boolean isReserving() {
        return this == RESERVING;
    }

    public boolean isReserved() {
        return this == RESERVED;
    }

    public boolean isUsed() {
        return this == USED;
    }

    public boolean isExpired() {
        return this == EXPIRED;
    }

    public boolean isCompleteStatus() {
        return this != RESERVED && this != USED && this != EXPIRED;
    }
}
