package com.woowacourse.thankoo.meeting.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.meeting.domain.MeetingStatus.ON_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.common.fixtures.ReservationFixture;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MeetingScheduleRepository 는 ")
@RepositoryTest
public class MeetingScheduleRepositoryTest {

    @Autowired
    private MeetingScheduleRepository meetingScheduleRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("원하는 날짜의 미팅을 완료 처리한다..")
    @Test
    void findAllByMeetingTimeDateBetween() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Reservation reservation = reservationRepository.save(
                ReservationFixture.createReservation(null, receiver, coupon));

        Meeting meeting = meetingRepository.save(
                new Meeting(
                        List.of(sender, receiver),
                        reservation.getMeetingTime(),
                        ON_PROGRESS,
                        coupon)
        );

        meetingScheduleRepository.updateMeetingStatus(
                MeetingStatus.FINISHED.name(),
                meeting.getMeetingTime().getDate(),
                ON_PROGRESS.name());

        Meeting updatedMeeting = meetingRepository.findById(meeting.getId()).get();

        assertThat(updatedMeeting.getMeetingStatus()).isEqualTo(MeetingStatus.FINISHED);
    }
}
