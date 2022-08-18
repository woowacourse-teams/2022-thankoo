package com.woowacourse.thankoo.reservation.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
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

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.common.exception.ForbiddenException;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("ReservationService 는 ")
@ApplicationTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("예약을 생성할 때 ")
    @Nested
    class SaveTest {

        @DisplayName("정상적으로 예약을 생성한다.")
        @Test
        void save() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));
            Coupon foundCoupon = couponRepository.findById(coupon.getId()).get();

            assertAll(
                    () -> assertThat(reservationId).isNotNull(),
                    () -> assertThat(foundCoupon.getCouponStatus()).isEqualTo(CouponStatus.RESERVING)
            );
        }

        @DisplayName("예약시 현재 이전일 경우 실패한다.")
        @Test
        void saveTimeFailed() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

            assertThatThrownBy(() ->
                    reservationService.save(receiver.getId(),
                            new ReservationRequest(coupon.getId(), LocalDateTime.now().minusSeconds(1L))))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("유효하지 않은 일정입니다.");
        }

        @DisplayName("예약시 쿠폰이 존재하지 않을 경우 에외가 발생한다.")
        @Test
        void isExistedCoupon() {
            Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));

            assertThatThrownBy(() -> reservationService.save(member.getId(),
                    new ReservationRequest(3L, LocalDateTime.now().plusDays(1L))))
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("존재하지 않는 쿠폰입니다.");
        }

        @DisplayName("예약을 보내는 회원이 쿠폰을 받은 회원이 아닐 경우 예외가 발생한다.")
        @Test
        void isSameReceiver() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Member other = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));

            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

            assertThatThrownBy(() -> reservationService.save(other.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L))))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약을 요청할 수 없는 회원입니다.");
        }
    }

    @DisplayName("예약을 승인한다.")
    @Test
    void updateStatusAccept() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Long reservationId = reservationService.save(receiver.getId(),
                new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));

        reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("accept"));

        Reservation foundReservation = reservationRepository.findById(reservationId).get();

        assertAll(
                () -> assertThat(foundReservation.getReservationStatus()).isEqualTo(ReservationStatus.ACCEPT),
                () -> assertThat(meetingRepository.findAll()).hasSize(1)
        );
    }

    @DisplayName("예약을 거절한다.")
    @Test
    void updateStatusDeny() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Long reservationId = reservationService.save(receiver.getId(),
                new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));

        reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("deny"));

        Reservation foundReservation = reservationRepository.findById(reservationId).get();

        assertAll(
                () -> assertThat(foundReservation.getReservationStatus()).isEqualTo(ReservationStatus.DENY),
                () -> assertThat(meetingRepository.findAll()).isEmpty()
        );
    }

    @DisplayName("예약이 거절되면 알람을 전송한다.")
    @Test
    void sendMessageThenUpdateStatusDeny() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Long reservationId = reservationService.save(receiver.getId(),
                new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));

        reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("deny"));
    }

    @DisplayName("예약을 취소할 때 ")
    @Nested
    class CancelTest {

        @DisplayName("예약자가 취소하면 성공한다.")
        @Test
        void cancel() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));

            reservationService.cancel(receiver.getId(), reservationId);

            Reservation foundReservation = reservationRepository.findById(reservationId).get();

            assertAll(
                    () -> assertThat(foundReservation.getReservationStatus()).isEqualTo(ReservationStatus.CANCELED),
                    () -> assertThat(couponRepository.findById(coupon.getId()).get().getCouponStatus()).isEqualTo(
                            NOT_USED)
            );
        }

        @DisplayName("예약자가 아닌 멤버가 취소하면 실패한다.")
        @Test
        void memberNotRequestMemberException() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));

            assertThatThrownBy(() -> reservationService.cancel(sender.getId(), reservationId))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage("권한이 없습니다.");
        }

        @DisplayName("예약 대기 중이 아닐경우 취소하면 실패한다.")
        @Test
        void reservationNotWaitingException() {
            Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
            Coupon coupon = couponRepository.save(
                    new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L)));

            reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("deny"));
            assertThatThrownBy(() -> reservationService.cancel(receiver.getId(), reservationId))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("예약 상태를 변경할 수 없습니다.");
        }
    }
}
