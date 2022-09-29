package com.woowacourse.thankoo.reservation.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponEventListener 는 ")
@ApplicationTest
class CouponEventListenerTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("쿠폰을 즉시 완료하면 예약이 승인으로 변경된다.")
    @Test
    void complete() {
        Coupon coupon = new Coupon(1L, 2L, new CouponContent(TYPE, TITLE, MESSAGE), NOT_USED);
        couponRepository.save(coupon);

        Reservation reservation = Reservation.reserve(LocalDateTime.now().plusDays(1L), TimeZoneType.ASIA_SEOUL,
                ReservationStatus.WAITING, 2L,
                coupon);

        Reservation savedReservation = reservationRepository.save(reservation);

        coupon.complete(2L);

        Reservation acceptReservation = reservationRepository.findById(savedReservation.getId()).get();

        assertThat(acceptReservation.getReservationStatus()).isEqualTo(ReservationStatus.ACCEPT);
    }
}
