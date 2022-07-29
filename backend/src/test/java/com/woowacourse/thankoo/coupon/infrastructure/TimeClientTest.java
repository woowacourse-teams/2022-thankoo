package com.woowacourse.thankoo.coupon.infrastructure;

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

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.common.fixtures.ReservationFixture;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("TimeClient 는 ")
@ApplicationTest
class TimeClientTest {

    @Autowired
    private TimeClient timeClient;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("쿠폰으로 조회할 때 ")
    @Nested
    class GetTimeTest {

        @DisplayName("예약 중이면 예약 정보를 조회한다.")
        @Test
        void getTimeResponseReservation() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Reservation reservation = reservationRepository.save(
                    ReservationFixture.createReservation(null, receiver, coupon));

            TimeResponse timeResponse = timeClient.getTimeResponse(coupon.getId(), CouponStatus.RESERVING);
            assertThat(timeResponse.getMeetingTime()).isEqualTo(reservation.getMeetingTime().getTime());
        }

        @DisplayName("예약 완료면 미팅 정보를 조회한다.")
        @Test
        void getTimeResponseMeeting() {
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
                            MeetingStatus.ON_PROGRESS,
                            coupon)
            );
            TimeResponse timeResponse = timeClient.getTimeResponse(coupon.getId(), CouponStatus.RESERVED);
            assertThat(timeResponse.getMeetingTime()).isEqualTo(meeting.getMeetingTime().getTime());
        }
    }
}
