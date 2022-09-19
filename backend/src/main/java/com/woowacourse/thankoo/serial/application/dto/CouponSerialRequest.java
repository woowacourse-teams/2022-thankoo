package com.woowacourse.thankoo.serial.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponSerialRequest {

    private Long memberId;
    private String couponType;
    private int quantity;

    public CouponSerialRequest(final Long memberId, final String couponType, final int quantity) {
        this.memberId = memberId;
        this.couponType = couponType;
        this.quantity = quantity;
    }
}
