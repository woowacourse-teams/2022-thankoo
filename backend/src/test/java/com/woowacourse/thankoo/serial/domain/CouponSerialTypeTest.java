package com.woowacourse.thankoo.serial.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CouponSerialType 는 ")
class CouponSerialTypeTest {

    @DisplayName("대소문자 구분없이 쿠폰 시리얼 타입을 조회한다.")
    @ParameterizedTest
    @CsvSource(value = {"coffee, COFFEE", "COFFEE, COFFEE", "meal, MEAL", "MEAL, MEAL"})
    void of(String name, CouponSerialType couponSerialType) {
        assertThat(CouponSerialType.of(name)).isEqualTo(couponSerialType);
    }
}
