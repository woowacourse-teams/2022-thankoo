package com.woowacourse.thankoo.coupon.domain;

import lombok.Getter;

@Getter
public class CouponImmediatelyUsedEvent {

    private final Long couponId;

    public CouponImmediatelyUsedEvent(final Long couponId) {
        this.couponId = couponId;
    }
}
