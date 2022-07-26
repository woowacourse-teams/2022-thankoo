package com.woowacourse.thankoo.meeting.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

@DisplayName("MeetingMembers 는 ")
class MeetingMembersTest {

    @DisplayName("회원이 두 명 이상일 경우 예외가 발생한다.")
    @Test
    void validateMemberCountException() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
        Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        Reservation reservation = createReservation(1L, skrr, coupon);
        MeetingMember meetingMember = new MeetingMember(huni, reservation);

        assertThatThrownBy(() -> new MeetingMembers(List.of(meetingMember)))
                .isInstanceOf(InvalidMeetingException.class)
                .hasMessage("미팅 참여자는 두명이어야 합니다.");
    }

    @DisplayName("회원을 포함하는지 검증할 떄 ")
    @Nested
    class HasMembersTest {

        @DisplayName("회원을 포함한다.")
        @Test
        void containsExactly() {
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = createReservation(1L, skrr, coupon);
            List<MeetingMember> meetingMembers = List.of(new MeetingMember(huni, reservation),
                    new MeetingMember(skrr, reservation));
            assertThat(new MeetingMembers(meetingMembers).notContainsExactly(huni.getId(), skrr.getId())).isFalse();
        }

        @DisplayName("회원을 포함하지 않는다.")
        @Test
        void notContainsExactly() {
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
            Member lala = new Member(3L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL);
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);
            Reservation reservation = createReservation(1L, skrr, coupon);
            List<MeetingMember> meetingMembers = List.of(new MeetingMember(huni, reservation),
                    new MeetingMember(skrr, reservation));
            assertThat(new MeetingMembers(meetingMembers).notContainsExactly(huni.getId(), lala.getId())).isTrue();
        }
    }
}
