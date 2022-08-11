package com.woowacourse.thankoo.coupon.presentation.dto;

import com.woowacourse.thankoo.coupon.domain.CouponTotal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponTotalResponse {

    private int sentCount;
    private int receivedCount;

    private CouponTotalResponse(final int sentCount, final int receivedCount) {
        this.sentCount = sentCount;
        this.receivedCount = receivedCount;
    }

    public static CouponTotalResponse from(final CouponTotal couponTotal) {
        return new CouponTotalResponse(couponTotal.getSentCount(), couponTotal.getReceivedCount());
    }
}
