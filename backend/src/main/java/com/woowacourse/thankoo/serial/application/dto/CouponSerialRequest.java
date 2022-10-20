package com.woowacourse.thankoo.serial.application.dto;

import lombok.Getter;

@Getter
public class CouponSerialRequest {

    private final Long memberId;
    private final String code;

    public CouponSerialRequest(final Long memberId, final String code) {
        this.memberId = memberId;
        this.code = code;
    }

    @Override
    public String toString() {
        return "CouponSerialRequest{" +
                "memberId=" + memberId +
                ", code='" + code + '\'' +
                '}';
    }
}
