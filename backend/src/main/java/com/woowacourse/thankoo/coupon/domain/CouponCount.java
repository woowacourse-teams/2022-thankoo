package com.woowacourse.thankoo.coupon.domain;

import lombok.Getter;

@Getter
public class CouponCount {

    private final int count;

    public CouponCount(final int count) {
        this.count = count;
    }
}
