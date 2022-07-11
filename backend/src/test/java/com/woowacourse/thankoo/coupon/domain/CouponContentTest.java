package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE_OVER;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE_OVER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CouponContent 는 ")
class CouponContentTest {

    @DisplayName("쿠폰 제목이 조건에 부합하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {TITLE_OVER, "", "   "})
    void titleOverLength(String title) {
        assertThatThrownBy(() -> new CouponContent(CouponType.COFFEE, title, MESSAGE))
                .isInstanceOf(InvalidCouponContentException.class)
                .hasMessage("잘못된 쿠폰 제목입니다.");
    }

    @DisplayName("쿠폰 내용이 조건에 부합하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {MESSAGE_OVER, "", "   "})
    void messageOverLength(String message) {
        assertThatThrownBy(() -> new CouponContent(CouponType.COFFEE, TITLE, message))
                .isInstanceOf(InvalidCouponContentException.class)
                .hasMessage("잘못된 쿠폰 내용입니다.");
    }
}
