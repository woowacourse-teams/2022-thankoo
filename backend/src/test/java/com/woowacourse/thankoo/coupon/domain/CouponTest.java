package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.IMMEDIATELY_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.RESERVED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.RESERVING;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Coupon 은 ")
class CouponTest {

    @DisplayName("자기 자신에게 쿠폰을 생성하면 실패한다.")
    @Test
    void validateSendBySenderSelf() {
        assertThatThrownBy(() -> new Coupon(1L, 1L, 1L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED))
                .isInstanceOf(InvalidCouponException.class)
                .hasMessage("쿠폰을 생성할 수 없습니다.");
    }

    @DisplayName("현재 쿠폰이 사용되지 않은 상태인지 확인한다.")
    @Test
    void isNotUsed() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);

        assertThat(coupon.isNotUsed()).isTrue();
    }

    @DisplayName("쿠폰을 받은 회원과 동일한 회원인지 확인한다.")
    @Test
    void isReceiver() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);

        assertThat(coupon.isReceiver(2L)).isTrue();
    }

    @DisplayName("쿠폰을 보낸 회원과 동일한 회원인지 확인한다.")
    @Test
    void isSender() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);

        assertThat(coupon.isSender(1L)).isTrue();
    }

    @DisplayName("쿠폰이 예약되면 예약중 상태로 변경된다.")
    @Test
    void reserve() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);
        coupon.reserve();

        assertThat(coupon.getCouponStatus()).isEqualTo(RESERVING);
    }

    @DisplayName("쿠폰이 예약 승인이 가능한 상태인지 확인한다.")
    @Test
    void canAcceptReservation() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);
        coupon.reserve();

        assertThat(coupon.canAcceptReservation()).isTrue();
    }

    @DisplayName("쿠폰을 사용되지 않은 상태로 변경한다.")
    @Test
    void setCouponStatusIsNotUsed() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);
        coupon.rollBack();

        assertThat(coupon.getCouponStatus()).isEqualTo(NOT_USED);
    }

    @DisplayName("쿠폰을 예약된 상태로 변경한다.")
    @Test
    void setCouponStatusIsReserved() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);
        coupon.accepted();

        assertThat(coupon.getCouponStatus()).isEqualTo(RESERVED);
    }

    @DisplayName("쿠폰이 예약된 상태이다.")
    @Test
    void isReserved() {
        Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), RESERVED);

        assertThat(coupon.isReserved()).isTrue();
    }

    @DisplayName("쿠폰을 사용할 때 ")
    @Nested
    class UseTest {

        @DisplayName("예약된 상태가 아닐경우 예외가 발생한다.")
        @ParameterizedTest
        @EnumSource(value = CouponStatus.class, names = "RESERVED", mode = Mode.EXCLUDE)
        void fail(CouponStatus couponStatus) {
            Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), couponStatus);

            assertThatThrownBy(coupon::use)
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("잘못된 쿠폰 상태입니다.");
        }

        @DisplayName("예약된 상태이면 쿠폰을 사용한다")
        @Test
        void success() {
            Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), RESERVED);

            coupon.use();
            assertThat(coupon.getCouponStatus()).isEqualTo(USED);
        }
    }

    @DisplayName("쿠폰을 즉시 사용할 때")
    @Nested
    class UsedImmediately {

        @DisplayName("쿠폰을 완료할 수 있는 상태가 아니라면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"RESERVED", "USED", "EXPIRED"})
        void invalidUseImmediatelyStatus(CouponStatus status) {
            Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), status);

            assertThatThrownBy(() -> coupon.useImmediately(1L))
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("쿠폰을 즉시 사용할 수 있는 상태가 아닙니다.");
        }

        @DisplayName("받는이와 보낸이가 아니라면 예외가 발생한다.")
        @Test
        void isNotSenderOrReceiver() {
            Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);

            assertThatThrownBy(() -> coupon.useImmediately(3L))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("쿠폰을 즉시 사용할 수 있는 회원이 아닙니다.");
        }

        @DisplayName("받는이 또는 보낸이고 완료할 수 있는 상태라면 사용한다.")
        @Test
        void useImmediately() {
            Coupon coupon = new Coupon(1L, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED);

            coupon.useImmediately(1L);

            assertThat(coupon.getCouponStatus()).isEqualTo(IMMEDIATELY_USED);
        }
    }
}
