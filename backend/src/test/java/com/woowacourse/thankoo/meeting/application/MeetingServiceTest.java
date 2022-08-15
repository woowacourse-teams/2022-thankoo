package com.woowacourse.thankoo.meeting.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.alarm.AlarmMessage;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.alarm.support.AlarmMessageRequest;
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
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Member other = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));
            reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("accept"));

            Meeting meeting = meetingRepository.findTopByCouponIdAndMeetingStatus(coupon.getId(),
                            MeetingStatus.ON_PROGRESS)
                    .get();

            assertThatThrownBy(() -> meetingService.complete(other.getId(), meeting.getId()))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage("권한이 없습니다.");
        }

        @DisplayName("미팅 참여자가 맞을 경우 성공한다.")
        @Test
        void success() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));
            reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("accept"));

            Meeting meeting = meetingRepository.findTopByCouponIdAndMeetingStatus(coupon.getId(),
                    MeetingStatus.ON_PROGRESS).get();

            meetingService.complete(sender.getId(), meeting.getId());

            Meeting foundMeeting = meetingRepository.findById(meeting.getId()).get();
            Coupon foundCoupon = couponRepository.findById(coupon.getId()).get();

            assertAll(
                    () -> assertThat(foundMeeting.getMeetingStatus()).isEqualTo(MeetingStatus.FINISHED),
                    () -> assertThat(foundCoupon.getCouponStatus()).isEqualTo(CouponStatus.USED)
            );
        }
    }

    @Test
    @DisplayName("해당 일자에 미팅이 존재하면 알람을 전송한다.")
    void sendMessageTodayMeetingMembers() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Long reservationId = reservationService.save(receiver.getId(),
                new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("accept"));

        meetingService.sendMessageTodayMeetingMembers(LocalDate.now().plusDays(1L));

        AlarmMessageRequest request = AlarmManager.getResources();

        assertAll(
                () -> assertThat(request.getEmails()).containsExactly(LALA_EMAIL, SKRR_EMAIL),
                () -> assertThat(request.getAlarmMessage()).isEqualTo(AlarmMessage.MEETING)
        );
    }

    @Test
    @DisplayName("해당 일자에 미팅이 존재하지 않으면 알람을 전송하지 않는다.")
    void sendMessageTodayMeetingMembersIsEmpty() {
        meetingService.sendMessageTodayMeetingMembers(LocalDate.now());

        assertThatThrownBy(AlarmManager::getResources)
                .hasMessage("전송하려는 알람이 존재하지 않습니다.")
                .isInstanceOf(InvalidAlarmException.class);
    }
}
