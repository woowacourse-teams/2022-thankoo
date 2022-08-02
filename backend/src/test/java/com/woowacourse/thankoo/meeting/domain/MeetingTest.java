package com.woowacourse.thankoo.meeting.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.createReservation;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.exception.ForbiddenException;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MeetingTest 는 ")
class MeetingTest {

    @DisplayName("회원을 검증한다.")
    @Test
    void validateMeetingMember() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
        Member lala = new Member(3L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);
        Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        Reservation reservation = createReservation(1L, skrr, coupon);

        assertThatThrownBy(
                () -> new Meeting(1L,
                        List.of(huni, lala),
                        reservation.getMeetingTime(),
                        MeetingStatus.ON_PROGRESS,
                        coupon))
                .isInstanceOf(InvalidMeetingException.class)
                .hasMessage("잘못된 미팅 참여자입니다.");
    }

    @DisplayName("회원이 두 명이 아닐 경우 예외가 발생한다.")
    @Test
    void validateMemberCountException() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
        Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        Reservation reservation = createReservation(1L, skrr, coupon);

        assertThatThrownBy(
                () -> new Meeting(1L,
                        List.of(huni),
                        reservation.getMeetingTime(),
                        MeetingStatus.ON_PROGRESS,
                        coupon))
                .isInstanceOf(InvalidMeetingException.class)
                .hasMessage("미팅 참여자는 두 명이어야 합니다.");
    }

    @DisplayName("미팅을 완료할 때 ")
    @Nested
    class CompleteTest {

        @DisplayName("회원이 참여자가 아닐 경우 예외가 발생한다.")
        @Test
        void notAttendeeException() {
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
            Member other = new Member(3L, HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL);
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = createReservation(1L, skrr, coupon);

            Meeting meeting = new Meeting(1L, List.of(huni, skrr), reservation.getMeetingTime(),
                    MeetingStatus.ON_PROGRESS,
                    coupon);

            assertThatThrownBy(() -> meeting.complete(other))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage("권한이 없습니다.");
        }

        @DisplayName("미팅이 진행 중이 아닐 경우 예외가 발생한다.")
        @Test
        void statusNotOnProgressException() {
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = createReservation(1L, skrr, coupon);

            Meeting meeting = new Meeting(1L, List.of(huni, skrr), reservation.getMeetingTime(), MeetingStatus.FINISHED,
                    coupon);

            assertThatThrownBy(() -> meeting.complete(huni))
                    .isInstanceOf(InvalidMeetingException.class)
                    .hasMessage("완료할 수 없는 상태입니다.");
        }

        @DisplayName("쿠폰 상태가 올바르지 않을 경우 예외가 발생한다.")
        @Test
        void invalidCouponException() {
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = createReservation(1L, skrr, coupon);

            Meeting meeting = new Meeting(1L, List.of(huni, skrr), reservation.getMeetingTime(), MeetingStatus.FINISHED,
                    coupon);

            assertThatThrownBy(() -> meeting.complete(huni))
                    .isInstanceOf(InvalidMeetingException.class)
                    .hasMessage("완료할 수 없는 상태입니다.");
        }
    }
}
