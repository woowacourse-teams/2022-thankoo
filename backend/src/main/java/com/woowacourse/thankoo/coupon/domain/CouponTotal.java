package com.woowacourse.thankoo.coupon.domain;

import lombok.Getter;

@Getter
public class CouponTotal {

    private final int sentCount;
    private final int receivedCount;

    public CouponTotal(final int sentCount, final int receivedCount) {
        this.sentCount = sentCount;
        this.receivedCount = receivedCount;
    }
}
