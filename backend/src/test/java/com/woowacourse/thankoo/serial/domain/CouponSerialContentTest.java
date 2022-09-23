package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE_OVER;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE_OVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CouponSerialContent 는 ")
class CouponSerialContentTest {

    @DisplayName("쿠폰 시리얼 제목의 앞/뒤에 공백이 있을 경우 제거한다.")
    @Test
    void CouponContentTitleTrim() {
        CouponSerialContent couponContent = new CouponSerialContent(" title ", MESSAGE);
        assertThat(couponContent.getTitle()).isEqualTo("title");
    }

    @DisplayName("쿠폰 시리얼 내용의 앞/뒤에 공백이 있을 경우 제거한다.")
    @Test
    void CouponContentMessageTrim() {
        CouponSerialContent couponContent = new CouponSerialContent(TITLE, " message message ");
        assertThat(couponContent.getMessage()).isEqualTo("message message");
    }

    @DisplayName("쿠폰 시리얼 제목이 조건에 부합하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {TITLE_OVER, "", "   "})
    void titleOverLength(String title) {
        assertThatThrownBy(() -> new CouponSerialContent(title, MESSAGE))
                .isInstanceOf(InvalidCouponSerialException.class)
                .hasMessage("잘못된 쿠폰 제목입니다.");
    }

    @DisplayName("쿠폰 시리얼 내용이 조건에 부합하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {MESSAGE_OVER, "", "   "})
    void messageOverLength(String message) {
        assertThatThrownBy(() -> new CouponSerialContent(TITLE, message))
                .isInstanceOf(InvalidCouponSerialException.class)
                .hasMessage("잘못된 쿠폰 내용입니다.");
    }
}
