package com.woowacourse.thankoo.coupon.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponUseRequest {

    private Long organizationId;

    public CouponUseRequest(final Long organizationId) {
        this.organizationId = organizationId;
    }
}
