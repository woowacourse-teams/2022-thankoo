package com.woowacourse.thankoo.coupon.application.dto;

import lombok.Getter;

@Getter
public class CouponUssCommand {

    private final Long memberId;
    private final Long organizationId;
    private final Long couponId;

    public CouponUssCommand(final Long memberId, final Long organizationId, final Long couponId) {
        this.memberId = memberId;
        this.organizationId = organizationId;
        this.couponId = couponId;
    }
}
