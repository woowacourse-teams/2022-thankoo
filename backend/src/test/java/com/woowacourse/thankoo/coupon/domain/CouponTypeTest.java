package com.woowacourse.thankoo.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.exception.InvalidCouponTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CouponType 은 ")
class CouponTypeTest {

    @DisplayName("쿠폰 타입 이름을 입력했을 때 ")
    @Nested
    class CouponTypeNameTest {

        @DisplayName("올바른 타입 이름이면 enum 값을 반환한다.")
        @ParameterizedTest(name = "{index} {displayName} value={0} expectedCouponType={1}")
        @CsvSource(value = {"coffee, COFFEE", "meal, MEAL"})
        void of(final String value, final CouponType expectedCouponType) {
            assertThat(CouponType.of(value)).isEqualTo(expectedCouponType);
        }

        @DisplayName("올바르지 않은 타입 이름이면 예외가 발생한다.")
        @Test
        void ofException() {
            assertThatThrownBy(() -> CouponType.of("invalidValue"))
                    .isInstanceOf(InvalidCouponTypeException.class)
                    .hasMessage("존재하지 않는 쿠폰 타입입니다.");
        }
    }
}
