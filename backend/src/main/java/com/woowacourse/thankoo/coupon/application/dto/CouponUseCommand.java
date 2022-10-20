package com.woowacourse.thankoo.coupon.application.dto;

import lombok.Getter;

@Getter
public class CouponUseCommand {

    private final Long memberId;
    private final Long organizationId;
    private final Long couponId;

    public CouponUseCommand(final Long memberId, final Long organizationId, final Long couponId) {
        this.memberId = memberId;
        this.organizationId = organizationId;
        this.couponId = couponId;
    }

    @Override
    public String toString() {
        return "CouponUseCommand{" +
                "memberId=" + memberId +
                ", organizationId=" + organizationId +
                ", couponId=" + couponId +
                '}';
    }
}
