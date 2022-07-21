package com.woowacourse.thankoo.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponStatus 는 ")
class CouponStatusTest {

    @DisplayName("쿠폰이 사용되지 않은 상태인지 확인한다.")
    @Test
    void isNotUsed() {
        CouponStatus couponStatus = CouponStatus.NOT_USED;

        assertThat(couponStatus.isNotUsed()).isTrue();
    }

    @DisplayName("쿠폰이 예약중인 상태인지 확인한다.")
    @Test
    void isReserving() {
        CouponStatus couponStatus = CouponStatus.RESERVING;

        assertThat(couponStatus.isReserving()).isTrue();
    }
}