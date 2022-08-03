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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.common.fixtures.ReservationFixture;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MeetingRepository 는 ")
@RepositoryTest
class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("쿠폰으로 미팅을 조회한다.")
    @Test
    void findMeetingByCoupon() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Reservation reservation = reservationRepository.save(
                ReservationFixture.createReservation(null, receiver, coupon));
        Meeting savedMeeting = meetingRepository.save(
                new Meeting(
                        List.of(sender, receiver),
                        reservation.getMeetingTime(),
                        MeetingStatus.ON_PROGRESS,
                        coupon)
        );
        Meeting foundMeeting = meetingRepository.findTopByCouponIdAndMeetingStatus(coupon.getId(),
                        MeetingStatus.ON_PROGRESS)
                .get();

        assertThat(foundMeeting).isEqualTo(savedMeeting);
    }

    @DisplayName("미팅의 상태와 날짜로 미팅을 모두 조회한다.")
    @Test
    void findAllByMeetingStatusAndMeetingTimeDate() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        for (int i = 0; i < 3; i++) {
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Reservation reservation = reservationRepository.save(
                    ReservationFixture.createReservation(null, receiver, coupon));
            meetingRepository.save(new Meeting(
                    List.of(sender, receiver),
                    reservation.getMeetingTime(),
                    MeetingStatus.ON_PROGRESS,
                    coupon)
            );
        }

        List<Meeting> meetings = meetingRepository.findAllByMeetingStatusAndMeetingTime_Date(
                MeetingStatus.ON_PROGRESS,
                LocalDate.now().plusDays(1L));

        assertThat(meetings).hasSize(3);
    }

    @DisplayName("만남의 상태와 미팅 id로 미팅 상태를 변경한다.")
    @Test
    void updateMeetingStatus() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        List<Long> meetingIds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Reservation reservation = reservationRepository.save(
                    ReservationFixture.createReservation(null, receiver, coupon));
            Meeting meeting = meetingRepository.save(new Meeting(
                    List.of(sender, receiver),
                    reservation.getMeetingTime(),
                    MeetingStatus.ON_PROGRESS,
                    coupon));
            meetingIds.add(meeting.getId());
        }

        meetingRepository.updateMeetingStatus(MeetingStatus.FINISHED, meetingIds);

        List<Meeting> meetings = meetingRepository.findAll();

        assertAll(
                () -> assertThat(meetings).hasSize(3),
                () -> assertThat(meetings).extracting("meetingStatus").containsOnly(MeetingStatus.FINISHED)
        );
    }
}
