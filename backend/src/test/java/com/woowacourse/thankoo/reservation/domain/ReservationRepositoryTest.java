package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.time;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("ReservationRepository 는 ")
@RepositoryTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("예약을 조회한다.")
    @Test
    void findWithCouponById() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Long reservationId = reservationRepository.save(Reservation.reserve(time(1L),
                TimeZoneType.ASIA_SEOUL,
                ReservationStatus.WAITING,
                receiver.getId(),
                coupon)).getId();
        Reservation reservation = reservationRepository.findWithCouponById(reservationId)
                .get();
        assertThat(reservation.getCoupon()).isEqualTo(coupon);
    }

    @DisplayName("쿠폰으로 예약을 조회한다.")
    @Test
    void findReservationByCoupon() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        Coupon coupon = couponRepository.save(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Reservation savedReservation = reservationRepository.save(
                Reservation.reserve(time(1L),
                        TimeZoneType.ASIA_SEOUL,
                        ReservationStatus.WAITING,
                        receiver.getId(),
                        coupon));

        Reservation foundReservation = reservationRepository.findTopByCouponIdAndReservationStatus(coupon.getId(),
                ReservationStatus.WAITING).get();

        assertThat(foundReservation).isEqualTo(savedReservation);
    }
}
