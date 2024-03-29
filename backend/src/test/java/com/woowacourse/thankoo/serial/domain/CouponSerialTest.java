package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CouponSerial 는 ")
class CouponSerialTest {

    @DisplayName("유효하지 않은 쿠폰 번호일경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"  ", "1234567", "123456789"})
    void invalidSerialCode(String code) {
        assertThatThrownBy(() -> new CouponSerial(1L, code, 1L, COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE))
                .isInstanceOf(InvalidCouponSerialException.class)
                .hasMessage("유효하지 않은 쿠폰 시리얼 번호입니다.");
    }

    @Test
    @DisplayName("코드 생성자를 통해 쿠폰 시리얼을 생성한다.")
    void createBySerialCreator() {
        CouponSerial couponSerial = new CouponSerial(1L, SERIAL_1, 1L, COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE);

        assertThat(couponSerial.getSerialCode()).isEqualTo(new SerialCode(SERIAL_1));
    }

    @DisplayName("사용된 쿠폰을 사용할 경우 예외가 발생한다.")
    @Test
    void useCouponSerialByUsedStatusException() {
        CouponSerial usedCouponSerial = new CouponSerial(1L, SERIAL_1, 1L, COFFEE, USED, NEO_TITLE, NEO_MESSAGE);

        assertThatThrownBy(usedCouponSerial::use)
                .isInstanceOf(InvalidCouponSerialException.class)
                .hasMessage("사용이 만료된 시리얼 번호입니다.");
    }
}
