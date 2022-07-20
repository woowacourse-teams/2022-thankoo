package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

@DisplayName("Reservation 는 ")
class ReservationTest {

    @DisplayName("예약을 요청을 할 때 ")
    @Nested
    class MeetingValidationTest {

        @DisplayName("예약 요청이 가능한 기간이면 통과한다.")
        @Test
        void isValidMeetingTime() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            assertThatNoException()
                    .isThrownBy(
                            () -> new Reservation(futureDate,
                                    TimeZoneType.ASIA_SEOUL,
                                    ReservationStatus.WAITING,
                                    receiverId,
                                    coupon)
                    );
        }

        @DisplayName("예약 요청이 불가능한 기간이면 예외가 발생한다.")
        @Test
        void invalidMeetingTime() {
            LocalDateTime futureDate = LocalDateTime.now().minusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            assertThatThrownBy(
                    () -> new Reservation(futureDate,
                            TimeZoneType.ASIA_SEOUL,
                            ReservationStatus.WAITING,
                            receiverId,
                            coupon))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("유효하지 않은 일정입니다.");
        }

        @DisplayName("쿠폰 수신인과 예약 요청자가 다르면 예외가 발생한다.")
        @Test
        void invalidReservationMember() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            assertThatThrownBy(() -> new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId + 1, coupon))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약을 요청할 수 없는 회원입니다.");
        }

        @DisplayName("쿠폰 상태가 NOT_USED 가 아니면 예외가 발생한다.")
        @ParameterizedTest
        @EnumSource(value = CouponStatus.class, names = "NOT_USED", mode = Mode.EXCLUDE)
        void invalidReservationCouponStatus(CouponStatus couponStatus) {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    couponStatus);
            assertThatThrownBy(() -> new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 요청이 불가능한 쿠폰입니다.");

        }
    }
}
