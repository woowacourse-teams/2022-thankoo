package com.woowacourse.thankoo.coupon.domain;

import lombok.Getter;

@Getter
public class CouponUsedEvent {

    private final Long couponId;

    public CouponUsedEvent(final Long couponId) {
        this.couponId = couponId;
    }
}
