package com.woowacourse.thankoo.meeting.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.time;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Meetings 는 ")
class MeetingsTest {

    @DisplayName("모든 미팅의 id를 조회한다.")
    @Test
    void getMeetingIds() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);

        Meetings result = new Meetings(createMeetings(huni, skrr));
        List<Long> meetingIds = result.getMeetingIds();

        assertThat(meetingIds).contains(1L, 2L, 3L);
    }

    @DisplayName("모든 미팅의 쿠폰들을 조회한다.")
    @Test
    void getCoupons() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);

        List<Meeting> meetings = createMeetings(huni, skrr);

        Meetings result = new Meetings(meetings);

        assertThat(result.getCoupons()).hasSize(3);
    }

    @DisplayName("모든 미팅에 참여한 회원들을 모두 조회한다.")
    @Test
    void getMembers() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);

        Meetings result = new Meetings(createMeetings(huni, skrr));
        Set<Member> members = result.getDistinctMembers();

        assertAll(
                () -> assertThat(members).hasSize(2),
                () -> assertThat(members).contains(huni, skrr)
        );
    }

    @DisplayName("미팅이 있는지 확인할 때 ")
    @Nested
    class HaveMeetingTest {

        @DisplayName("없다면 false 를 반환한다.")
        @Test
        void noMeeting() {
            Meetings meetings = new Meetings(Collections.emptyList());
            assertThat(meetings.haveMeeting()).isFalse();
        }

        @DisplayName("있다면 true 를 반환한다.")
        @Test
        void haveMeeting() {
            Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
            Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);

            Meetings result = new Meetings(createMeetings(huni, skrr));

            assertThat(result.haveMeeting()).isTrue();
        }

    }

    private static List<Meeting> createMeetings(Member member1, Member member2) {
        List<Meeting> meetings = new ArrayList<>();
        for (int id = 0; id < 3; id++) {
            Coupon coupon = new Coupon(1L, 1L, member1.getId(), member2.getId(),
                    new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            Reservation reservation = Reservation.reserve(time(1L),
                    TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    member2.getId(),
                    coupon);

            meetings.add(new Meeting(id + 1L,
                    List.of(member1, member2),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon));
        }
        return meetings;
    }
}
