package com.woowacourse.thankoo.meeting.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.meeting.application.dto.MeetingQueryCondition;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("MeetingQueryRepository 는 ")
@RepositoryTest
class MeetingQueryRepositoryTest {

    private MeetingQueryRepository meetingQueryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MeetingRepository meetingRepository;

    private ReservedMeetingCreator reservedMeetingCreator;

    @BeforeEach
    void setUp() {
        meetingQueryRepository = new MeetingQueryRepository(new NamedParameterJdbcTemplate(dataSource));
        reservedMeetingCreator = new ReservationMeetingService(meetingRepository, memberRepository);
    }

    @DisplayName("회원의 미팅을 조회한다.")
    @Test
    void findMeetingsByMemberId() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        List<Coupon> coupons = couponRepository.saveAll(List.of(
                new Coupon(lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED),
                new Coupon(lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED),
                new Coupon(lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED))
        );

        LocalDateTime meetingDate = LocalDateTime.now().plusDays(1L);

        for (Coupon coupon : coupons) {
            Reservation reservation = reservationRepository.save(
                    Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, skrr.getId(),
                            coupon));
            reservation.update(lala, ReservationStatus.ACCEPT, reservedMeetingCreator);
        }

        MeetingQueryCondition condition = new MeetingQueryCondition(lala.getId(), LocalDateTime.now(),
                MeetingStatus.ON_PROGRESS.name());
        List<MeetingCoupon> meetingsByMembers = meetingQueryRepository.findMeetingsByMemberIdAndTimeAndStatus(
                condition);

        assertAll(
                () -> assertThat(meetingsByMembers).hasSize(3),
                () -> assertThat(meetingsByMembers).extracting("memberName").containsOnly(SKRR_NAME)
        );
    }
}
