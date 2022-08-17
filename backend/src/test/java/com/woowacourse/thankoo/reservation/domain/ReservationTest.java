package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL_SKRR;
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

import com.woowacourse.thankoo.common.exception.ForbiddenException;
import com.woowacourse.thankoo.common.fake.FakeReservedMeetingCreator;
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
            LocalDateTime futureDate = LocalDateTime.now().plusMinutes(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            assertAll(
                    () -> assertThatNoException()
                            .isThrownBy(
                                    () -> Reservation.reserve(futureDate,
                                            TimeZoneType.ASIA_SEOUL,
                                            ReservationStatus.WAITING,
                                            receiverId,
                                            coupon)
                            ),
                    () -> assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.RESERVING)
            );
        }

        @DisplayName("시간이 현재 이전이면 예외가 발생한다.")
        @Test
        void invalidTime() {
            LocalDateTime futureDate = LocalDateTime.now().minusSeconds(1L);
            Long receiverId = 2L;

            Coupon coupon = new Coupon(1L, receiverId, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            assertThatThrownBy(() -> Reservation.reserve(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                    receiverId, coupon))
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

            assertThatThrownBy(() -> Reservation.reserve(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
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
            assertThatThrownBy(() -> Reservation.reserve(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
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
            Reservation reservation = Reservation.reserve(futureDate,
                    TimeZoneType.ASIA_SEOUL,
                    reservationStatus,
                    receiverId,
                    coupon);

            assertThatThrownBy(
                    () -> reservation.update(new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR),
                            ReservationStatus.ACCEPT, new FakeReservedMeetingCreator()))
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
            Reservation reservation = Reservation.reserve(futureDate,
                    TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    receiverId,
                    coupon);

            assertThatThrownBy(
                    () -> reservation.update(new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR),
                            ReservationStatus.WAITING, new FakeReservedMeetingCreator()))
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
            Reservation reservation = Reservation.reserve(futureDate, TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    receiverId, coupon);

            assertThatThrownBy(
                    () -> reservation.update(
                            new Member(receiverId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR),
                            ReservationStatus.ACCEPT,
                            new FakeReservedMeetingCreator()))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }

        @DisplayName("coupon 의 상태가 예약 중이 아닐 경우 예외가 발생한다.")
        @Test
        void updateCouponStatusException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR);
            Member hoho = new Member(2L, HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL_SKRR);
            Coupon coupon = new Coupon(huni.getId(), hoho.getId(), new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = Reservation.reserve(futureDate, TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING, hoho.getId(), coupon);
            reservation.cancel(hoho);

            assertThatThrownBy(
                    () -> reservation.update(
                            new Member(huni.getId(), LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR),
                            ReservationStatus.ACCEPT,
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
            Reservation reservation = Reservation.reserve(futureDate,
                    TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    receiverId,
                    coupon);

            reservation.update(new Member(senderId, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR),
                    ReservationStatus.ACCEPT,
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
            Member sender = new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR);
            Member receiver = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR);

            Coupon coupon = new Coupon(sender.getId(), receiver.getId(),
                    new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = Reservation.reserve(futureDate,
                    TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    receiver.getId(),
                    coupon);

            assertThatThrownBy(() -> reservation.cancel(sender))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage("권한이 없습니다.");
        }

        @DisplayName("예약 상태가 waiting이 아닐 경우 실패한다.")
        @Test
        void reservationNotWaitingException() {
            LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);
            Member sender = new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR);
            Member receiver = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR);

            Coupon coupon = new Coupon(sender.getId(), receiver.getId(),
                    new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = Reservation.reserve(futureDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.ACCEPT,
                    receiver.getId(), coupon);

            assertThatThrownBy(() -> reservation.cancel(receiver))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }
    }
}
