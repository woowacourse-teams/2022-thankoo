package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponTypeException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum CouponType {

    COFFEE("coffee"),
    MEAL("meal");

    private final String value;

    CouponType(final String value) {
        this.value = value;
    }

    public static CouponType of(final String name) {
        return Arrays.stream(values())
                .filter(it -> it.getValue().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new InvalidCouponTypeException(ErrorType.INVALID_COUPON_TYPE));
    }
}
