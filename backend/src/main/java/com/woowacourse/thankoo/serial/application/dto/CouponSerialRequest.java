package com.woowacourse.thankoo.serial.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponSerialRequest {

    private String serialCode;

    public CouponSerialRequest(final String serialCode) {
        this.serialCode = serialCode;
    }
}
