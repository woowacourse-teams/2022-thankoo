package com.woowacourse.thankoo.coupon.domain;

import lombok.Getter;

@Getter
public class CouponCompleteEvent {

    private final Long couponId;

    public CouponCompleteEvent(final Long couponId) {
        this.couponId = couponId;
    }
}
