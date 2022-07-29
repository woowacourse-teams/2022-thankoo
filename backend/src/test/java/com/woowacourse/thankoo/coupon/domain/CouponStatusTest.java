package com.woowacourse.thankoo.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CouponStatus 는 ")
class CouponStatusTest {

    @DisplayName("쿠폰 상태를 조회한다.")
    @ParameterizedTest(name = "{index} {displayName} name={0}, status={1}")
    @CsvSource(value = {"not_used:NOT_USED", "reserving:RESERVING", "reserved:RESERVED", "used:USED",
            "expired:EXPIRED"}, delimiter = ':')
    void of(String name, CouponStatus status) {
        assertThat(CouponStatus.of(name)).isSameAs(status);
    }

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
