package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponSerials 는 ")
class SerialCodesTest {

    @DisplayName("시리얼 번호가 중복되는 경우 예외를 발생한다.")
    @Test
    void duplicateSerialCode() {
        assertThatThrownBy(() -> new SerialCodes(List.of(
                new SerialCode(SERIAL_1), new SerialCode(SERIAL_1))))
                .isInstanceOf(InvalidCouponSerialException.class)
                .hasMessage("시리얼 번호가 중복됩니다.");
    }

    @DisplayName("시리얼 코드를 100개를 초과하는 경우 예외를 발생한다.")
    @Test
    void overSize() {
        List<SerialCode> codes = new ArrayList<>();
        for (int i = 100; i <= 200; i++) {
            codes.add(new SerialCode("10000" + i));
        }

        assertThatThrownBy(() -> new SerialCodes(codes))
                .isInstanceOf(InvalidCouponSerialException.class)
                .hasMessage("생성할 수 있는 시리얼 번호를 초과했습니다.");
    }

    @DisplayName("개수와 코드 생성 방식으로 시리얼번호를 생성한다.")
    @Test
    void of() {
        SerialCodes serialCodes = SerialCodes.of(1, new TestSerialCodeCreator());

        assertThat(serialCodes.getValues()).hasSize(1);
    }
}
