package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.TestFixture.MESSAGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CouponContent 는 ")
class CouponContentTest {

    private static final String TITLE_OVER = "abcdefghijklmnopqrstu";

    @DisplayName("쿠폰 제목이 조건에 부합하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {TITLE_OVER, ""})
    void titleOverLength(String title) {
        assertThatThrownBy(() -> new CouponContent(CouponType.COFFEE, title, MESSAGE))
                .isInstanceOf(InvalidCouponContentException.class)
                .hasMessage(ErrorType.INVALID_COUPON_TITLE.getMessage());
    }
}
