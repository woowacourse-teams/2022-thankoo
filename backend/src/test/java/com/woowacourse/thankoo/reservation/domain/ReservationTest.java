package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.fake.FakeReservedMeetingCreator;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.meeting.domain.MeetingTime;
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
    class UpdateTest {

        @DisplayName("예약 상태가 대기 중이 아닐 경우 예외가 발생한다.")
        @ParameterizedTest
        @EnumSource(value = ReservationStatus.class, names = {"WAITING"}, mode = Mode.EXCLUDE)
        void updateReservationNotWaitingException(ReservationStatus reservationStatus) {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, reservationStatus,
                    receiverId, coupon);
            reservation.reserve();

            assertThatThrownBy(
                    () -> reservation.update(new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL),
                            "accept", new FakeReservedMeetingCreator()))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("불가능한 상태로 변경 요청할 경우 예외가 발생한다.")
        @Test
        void updateInvalidStatusException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon);
            reservation.reserve();

            assertThatThrownBy(
                    () -> reservation.update(new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL),
                            "waiting", new FakeReservedMeetingCreator()))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("예약 승인 회원과 쿠폰 발급자가 다를 경우 예외가 발생한다.")
        @Test
        void updateMemberNotMatchException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon);
            reservation.reserve();

            assertThatThrownBy(
                    () -> reservation.update(
                            new Member(receiverId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL), "accept",
                            new FakeReservedMeetingCreator()))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("coupon 의 상태가 예약 중이 아닐 경우 예외가 발생한다.")
        @Test
        void updateCouponStatusException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;
            Long senderId = 1L;

            Coupon coupon = new Coupon(senderId, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon);

            assertThatThrownBy(
                    () -> reservation.update(
                            new Member(senderId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL), "accept",
                            new FakeReservedMeetingCreator()))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("정상적으로 예약을 승인한다.")
        @Test
        void update() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Long receiverId = 2L;
            Long senderId = 1L;

            Coupon coupon = new Coupon(senderId, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(1L,
                    new MeetingTime(futureDate.toLocalDate(), futureDate, TimeZoneType.ASIA_SEOUL.getId()),
                    ReservationStatus.WAITING,
                    receiverId, coupon);
            reservation.reserve();

            reservation.update(new Member(senderId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL), "accept",
                    new FakeReservedMeetingCreator());

            assertAll(
                    () -> assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.ACCEPT),
                    () -> assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.RESERVED)
            );
        }
    }

    @DisplayName("예약을 취소할 떄 ")
    @Nested
    class CancelTest {

        @DisplayName("예약자가 아닐 경우 실패한다.")
        @Test
        void memberNotOwnerException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Member sender = new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);
            Member receiver = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);

            Coupon coupon = new Coupon(sender.getId(), receiver.getId(),
                    new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(1L,
                    new MeetingTime(futureDate.toLocalDate(), futureDate, TimeZoneType.ASIA_SEOUL.getId()),
                    ReservationStatus.WAITING,
                    receiver.getId(), coupon);
            reservation.reserve();

            assertThatThrownBy(() -> reservation.cancel(sender))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("예약자가 아닐 경우 실패한다.")
        @Test
        void reservationNotWaitingException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Member sender = new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);
            Member receiver = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);

            Coupon coupon = new Coupon(sender.getId(), receiver.getId(),
                    new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = new Reservation(1L,
                    new MeetingTime(futureDate.toLocalDate(), futureDate, TimeZoneType.ASIA_SEOUL.getId()),
                    ReservationStatus.ACCEPT,
                    receiver.getId(), coupon);

            assertThatThrownBy(() -> reservation.cancel(receiver))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }
    }

}
