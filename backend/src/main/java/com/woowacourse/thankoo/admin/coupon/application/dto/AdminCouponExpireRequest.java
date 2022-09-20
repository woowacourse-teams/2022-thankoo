package com.woowacourse.thankoo.admin.coupon.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminCouponExpireRequest {

    private List<Long> couponIds;

    public AdminCouponExpireRequest(final List<Long> couponIds) {
        this.couponIds = couponIds;
    }

    @Override
    public String toString() {
        return "AdminCouponExpireRequest{" +
                "couponIds=" + couponIds +
                '}';
    }
}
