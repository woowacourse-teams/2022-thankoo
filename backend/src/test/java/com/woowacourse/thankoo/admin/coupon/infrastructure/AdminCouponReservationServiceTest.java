package com.woowacourse.thankoo.admin.coupon.infrastructure;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static com.woowacourse.thankoo.reservation.domain.ReservationStatus.CANCELED;
import static com.woowacourse.thankoo.reservation.domain.ReservationStatus.WAITING;
import static com.woowacourse.thankoo.reservation.domain.TimeZoneType.ASIA_SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.reservation.domain.AdminReservationRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminCouponReservationService 는 ")
@ApplicationTest
class AdminCouponReservationServiceTest {

    @Autowired
    private AdminCouponReservationService adminCouponReservationService;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @Autowired
    private AdminCouponRepository adminCouponRepository;

    @Autowired
    private AdminReservationRepository adminReservationRepository;

    @DisplayName("쿠폰들의 예약을 취소한다.")
    @Test
    void cancelReservation() {
        Member sender = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        Coupon coffeeCoupon = adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon mealCoupon = adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), NOT_USED));

        LocalDateTime localDateTime = LocalDateTime.now().plusDays(2L);

        Reservation coffeeReservation = adminReservationRepository.save(
                Reservation.reserve(localDateTime, ASIA_SEOUL, WAITING, receiver.getId(), coffeeCoupon));
        Reservation mealReservation = adminReservationRepository.save(
                Reservation.reserve(localDateTime, ASIA_SEOUL, WAITING, receiver.getId(), mealCoupon));

        adminCouponReservationService.cancelReservation(List.of(coffeeCoupon, mealCoupon));

        List<Reservation> reservations = adminReservationRepository.findAllById(
                List.of(coffeeReservation.getId(), mealReservation.getId()));

        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations).extracting("reservationStatus").containsOnly(CANCELED)
        );
    }
}
