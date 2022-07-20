package com.woowacourse.thankoo.coupon.domain;

public enum CouponStatus {

    NOT_USED,
    RESERVED,
    USED,
    EXPIRED;

    public boolean isNotUsed() {
        return this == NOT_USED;
    }
}
