package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Coupon 은 ")
class CouponTest {

    @DisplayName("현재 쿠폰이 사용되지 않은 상태인지 확인한다.")
    @Test
    void isNotUsed() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);

        assertThat(coupon.isNotUsed()).isTrue();
    }

    @DisplayName("쿠폰을 받은 회원과 동일한 회원인지 확인한다.")
    @Test
    void isReceiver() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);

        assertThat(coupon.isReceiver(2L)).isTrue();
    }

    @DisplayName("쿠폰을 보낸 회원과 동일한 회원인지 확인한다.")
    @Test
    void isSender() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);

        assertThat(coupon.isSender(1L)).isTrue();
    }

    @DisplayName("쿠폰이 예약되면 예약중 상태로 변경된다.")
    @Test
    void reserve() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        coupon.reserve();

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.RESERVING);
    }

    @DisplayName("쿠폰이 예약 승인이 가능한 상태인지 확인한다.")
    @Test
    void canAcceptReservation() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        coupon.reserve();

        assertThat(coupon.canAcceptReservation()).isTrue();
    }

    @DisplayName("쿠폰을 사용되지 않은 상태로 변경한다.")
    @Test
    void setCouponStatusIsNotUsed() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        coupon.denied();

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.NOT_USED);
    }

    @DisplayName("쿠폰을 예약된 상태로 변경한다.")
    @Test
    void setCouponStatusIsReserved() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        coupon.accepted();

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.RESERVED);
    }
}
