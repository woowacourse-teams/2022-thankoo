package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.member.domain.Member;
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

    @DisplayName("예약을 생성 할 때 ")
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

    @DisplayName("예약 상태를 변경할 때 ")
    @Nested
    class UpdateReservationStatusTest {

        @DisplayName("예약 상태가 대기 중이 아닐 경우 예외가 발생한다.")
        @ParameterizedTest
        @EnumSource(value = ReservationStatus.class, names = {"WAITING"}, mode = Mode.EXCLUDE)
        void updateStatusReservationNotWaitingException(ReservationStatus reservationStatus) {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, reservationStatus,
                    receiverId, coupon);
            reservation.reserve();

            assertThatThrownBy(
                    () -> reservation.updateStatus(new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL),
                            "accept"))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("불가능한 상태로 변경 요청할 경우 예외가 발생한다.")
        @Test
        void updateStatusInvalidStatusException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon);
            reservation.reserve();

            assertThatThrownBy(
                    () -> reservation.updateStatus(new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL),
                            "waiting"))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("예약 승인 회원과 쿠폰 발급자가 다를 경우 예외가 발생한다.")
        @Test
        void updateStatusMemberNotMatchException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon);
            reservation.reserve();

            assertThatThrownBy(
                    () -> reservation.updateStatus(
                            new Member(receiverId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL), "accept"))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("coupon 의 상태가 예약 중이 아닐 경우 예외가 발생한다.")
        @Test
        void updateStatusCouponStatusException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;
            Long senderId = 1L;

            Coupon coupon = new Coupon(senderId, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon);

            assertThatThrownBy(
                    () -> reservation.updateStatus(
                            new Member(senderId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL), "accept"))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("정상적으로 예약을 승인한다.")
        @Test
        void updateStatus() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;
            Long senderId = 1L;

            Coupon coupon = new Coupon(senderId, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon);
            reservation.reserve();

            reservation.updateStatus(new Member(senderId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL), "accept");

            assertAll(
                    () -> assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.ACCEPT),
                    () -> assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.RESERVED)
            );
        }
    }
}
