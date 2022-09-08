package com.woowacourse.thankoo.meeting.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.common.exception.ForbiddenException;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.reservation.application.ReservationService;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MeetingService 는 ")
@ApplicationTest
class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("미팅을 완료할 때 ")
    @Nested
    class MeetingCompleteTest {

        @DisplayName("미팅의 참여자가 아닐 경우 미팅을 완료하지 못 한다.")
        @Test
        void notAttendeeException() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
            Member other = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));
            reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("accept"));

            Meeting meeting = meetingRepository.findTopByCouponId(coupon.getId()).get();

            assertThatThrownBy(() -> meetingService.complete(other.getId(), meeting.getId()))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage("권한이 없습니다.");
        }

        @DisplayName("미팅 참여자가 맞을 경우 성공한다.")
        @Test
        void success() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));
            reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("accept"));

            Meeting meeting = meetingRepository.findTopByCouponId(coupon.getId()).get();

            meetingService.complete(sender.getId(), meeting.getId());

            Meeting foundMeeting = meetingRepository.findById(meeting.getId()).get();
            Coupon foundCoupon = couponRepository.findById(coupon.getId()).get();

            assertAll(
                    () -> assertThat(foundMeeting.getMeetingStatus()).isEqualTo(MeetingStatus.FINISHED),
                    () -> assertThat(foundCoupon.getCouponStatus()).isEqualTo(CouponStatus.USED)
            );
        }
    }

    @DisplayName("오늘 있었던 미팅을 완료할 때 ")
    @Nested
    class DateMeetingCompleteTest {

        @DisplayName("만남이 있으면 완료시킨다.")
        @Test
        void completeIfMeetingExist() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

            Coupon coupon1 = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId1 = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon1.getId(), LocalDateTime.now().plusDays(1L)));

            Coupon coupon2 = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId2 = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon2.getId(), LocalDateTime.now().plusDays(1L)));
            reservationService.updateStatus(sender.getId(), reservationId1, new ReservationStatusRequest("accept"));
            reservationService.updateStatus(sender.getId(), reservationId2, new ReservationStatusRequest("accept"));

            LocalDate date = LocalDate.now().plusDays(1L);
            meetingService.complete(date);

            assertThat(meetingRepository.findAllByMeetingStatusAndTimeUnit_Date(MeetingStatus.FINISHED, date)).hasSize(
                    2);
        }

        @DisplayName("만남이 없을 경우에 예외가 발생하지 않는다.")
        @Test
        void completeNoMeeting() {
            assertDoesNotThrow(() -> meetingService.complete(LocalDate.now()));
        }
    }
}

