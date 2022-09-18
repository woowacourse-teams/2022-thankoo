package com.woowacourse.thankoo.serial.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CouponSerial 는 ")
public class CouponSerialTest {

    @DisplayName("유효하지 않은 쿠폰 번호일경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"  ", "1234567", "123456789"})
    void invalidSerialCode(String code) {
        assertThatThrownBy(() -> new CouponSerial(code, 1L, CouponSerialType.COFFEE))
                .isInstanceOf(InvalidCouponSerialException.class)
                .hasMessage("유효하지 않은 쿠폰 시리얼 번호입니다.");
    }
}