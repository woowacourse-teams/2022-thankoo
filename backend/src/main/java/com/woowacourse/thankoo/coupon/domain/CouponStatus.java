package com.woowacourse.thankoo.coupon.domain;

public enum CouponStatus {

    NOT_USED,
    RESERVING,
    RESERVED,
    USED,
    EXPIRED;

    public boolean isNotUsed() {
        return this == NOT_USED;
    }

    public boolean isReserving() {
        return this == RESERVING;
    }
}
