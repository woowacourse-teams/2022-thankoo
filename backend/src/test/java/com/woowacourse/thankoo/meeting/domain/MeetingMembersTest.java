package com.woowacourse.thankoo.meeting.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.MeetingTime;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
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
        MeetingMember meetingMember = new MeetingMember(huni,
                new Reservation(1L,
                        new MeetingTime(LocalDate.now().plusDays(1L),
                                LocalDateTime.now().plusDays(1L),
                                TimeZoneType.ASIA_SEOUL.getId()),
                        ReservationStatus.WAITING,
                        skrr.getId(),
                        coupon));
        assertThatThrownBy(() -> new MeetingMembers(List.of(meetingMember)))
                .isInstanceOf(InvalidMeetingException.class)
                .hasMessage("미팅 참여자는 두명이어야 합니다.");
    }

}
