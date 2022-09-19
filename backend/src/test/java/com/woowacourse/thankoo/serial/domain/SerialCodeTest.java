package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SerialCode 는 ")
class SerialCodeTest {

    @Test
    @DisplayName("코드가 같은지 확인한다.")
    void equalsCode() {
        SerialCode couponSerial1 = new SerialCode(SERIAL_1);
        SerialCode couponSerial2 = new SerialCode(SERIAL_1);

        assertThat(couponSerial1.equals(couponSerial2)).isTrue();
    }
}
