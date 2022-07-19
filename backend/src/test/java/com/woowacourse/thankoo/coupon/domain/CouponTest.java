package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Coupon 은 ")
class CouponTest {

    @DisplayName("현재 쿠폰이 사용되지 않은 상태인지 확인한다.")
    @ParameterizedTest(name = "{index} {displayName} couponStatus={0} expectedResult={1}")
    @CsvSource(value = {"NOT_USED, true", "RESERVED, false", "USED, false", "EXPIRED, false"})
    void isSameCouponStatus(final CouponStatus couponStatus, final boolean expectedResult) {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE), CouponStatus.NOT_USED);

        assertThat(coupon.isSameCouponStatus(couponStatus)).isEqualTo(expectedResult);
    }
}