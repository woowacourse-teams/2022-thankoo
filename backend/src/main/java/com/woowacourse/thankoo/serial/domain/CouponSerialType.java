package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum CouponSerialType {

    COFFEE("COFFEE"),
    MEAL("MEAL");

    private final String value;

    CouponSerialType(final String value) {
        this.value = value;
    }

    public static CouponSerialType of(final String name) {
        return Arrays.stream(values())
                .filter(it -> it.getValue().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new InvalidCouponContentException(ErrorType.INVALID_COUPON_TYPE));
    }
}
