package com.woowacourse.thankoo.meeting.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
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
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Meetings 는 ")
class MeetingsTest {

    @DisplayName("모든 미팅의 id를 조회한다.")
    @Test
    void getMeetingIds() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);

        List<Meeting> meetings = new ArrayList<>();
        for (int id = 0; id < 3; id++) {
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            Reservation reservation = Reservation.reserve(time(1L),
                    TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    skrr.getId(),
                    coupon);

            meetings.add(new Meeting(id + 1L,
                    List.of(huni, skrr),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon));
        }
        Meetings result = new Meetings(meetings);
        List<Long> meetingIds = result.getMeetingIds();

        assertThat(meetingIds).contains(1L, 2L, 3L);
    }

    @DisplayName("모든 미팅의 쿠폰들을 조회한다.")
    @Test
    void getCoupons() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);

        List<Meeting> meetings = new ArrayList<>();
        for (int id = 0; id < 3; id++) {
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            Reservation reservation = Reservation.reserve(time(1L),
                    TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    skrr.getId(),
                    coupon);

            meetings.add(new Meeting(id + 1L,
                    List.of(huni, skrr),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon));
        }

        Meetings result = new Meetings(meetings);

        assertThat(result.getCoupons()).hasSize(3);
    }

    @DisplayName("모든 미팅에 참여한 회원들을 모두 조회한다.")
    @Test
    void getMembers() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);

        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED);

            Reservation reservation = Reservation.reserve(time(1L),
                    TimeZoneType.ASIA_SEOUL,
                    ReservationStatus.WAITING,
                    skrr.getId(),
                    coupon);

            meetings.add(new Meeting(1L,
                    List.of(huni, skrr),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon));
        }
        Meetings result = new Meetings(meetings);
        List<Member> members = result.getMembers();

        assertAll(
                () -> assertThat(members).hasSize(6),
                () -> assertThat(members).contains(huni, skrr)
        );
    }
}
