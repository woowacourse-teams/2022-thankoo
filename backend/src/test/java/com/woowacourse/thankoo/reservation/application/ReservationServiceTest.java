package com.woowacourse.thankoo.reservation.application;

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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("ReservationService 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("예약을 생성한다.")
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

    @DisplayName("예약시 쿠폰이 존재하는지 검증한다.")
    @Test
    void isExistedCoupon() {
        Long memberId = 1L;

        assertThatThrownBy(() -> reservationService.save(memberId,
                new ReservationRequest(3L, LocalDateTime.now().plusDays(1L))))
                .isInstanceOf(InvalidCouponException.class)
                .hasMessage("존재하지 않는 쿠폰입니다.");
    }

    @DisplayName("예약을 보내는 회원과 쿠폰을 받은 회원이 같은지 검증한다.")
    @Test
    void isSameReceiver() {
        Long receiverId = 2L;
        Long invalidReceiverId = 3L;

        Coupon coupon = couponRepository.save(
                new Coupon(1L, receiverId, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

        assertThatThrownBy(() -> reservationService.save(invalidReceiverId,
                new ReservationRequest(coupon.getId(), LocalDateTime.now().plusDays(1L))))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    void getReservations() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        Coupon coupon1 = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon coupon2 = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon coupon3 = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

        reservationService.save(receiver.getId(),
                new ReservationRequest(coupon1.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(receiver.getId(),
                new ReservationRequest(coupon2.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(receiver.getId(),
                new ReservationRequest(coupon3.getId(), LocalDateTime.now().plusDays(1L)));

        List<ReservationResponse> reservations = reservationService.getReservations(receiver.getId());

        assertThat(reservations.size()).isEqualTo(3);
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
        assertThat(foundReservation.getReservationStatus()).isEqualTo(ReservationStatus.ACCEPT);
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
}
