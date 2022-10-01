package com.woowacourse.thankoo.coupon.domain;

import lombok.Getter;

@Getter
public class CouponCompletedEvent {

    private final Long couponId;

    public CouponCompletedEvent(final Long couponId) {
        this.couponId = couponId;
    }
}
