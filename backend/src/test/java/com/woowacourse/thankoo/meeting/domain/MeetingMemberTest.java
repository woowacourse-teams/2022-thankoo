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
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.domain.TimeUnit;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MeetingMember 는 ")
class MeetingMemberTest {

    @DisplayName("멤버가 참여자 인지를 검증한다.")
    @Test
    void has() {
        Member huni = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL);
        Coupon coupon = new Coupon(1L, huni.getId(), skrr.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);

        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1L);
        Meeting meeting = new Meeting(1L, List.of(huni, skrr),
                new TimeUnit(localDateTime.toLocalDate(), localDateTime, TimeZoneType.ASIA_SEOUL.getId()),
                MeetingStatus.ON_PROGRESS, coupon);

        MeetingMember meetingMember = new MeetingMember(huni, meeting);
        assertThat(meetingMember.has(huni)).isTrue();
    }

}
