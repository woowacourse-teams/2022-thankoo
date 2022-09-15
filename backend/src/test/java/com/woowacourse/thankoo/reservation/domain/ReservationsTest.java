package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Reservations 는 ")
class ReservationsTest {

    @DisplayName("예약 id 들을 가져온다.")
    @Test
    void getIds() {
        Coupon coupon1 = new Coupon(101L, 1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        Coupon coupon2 = new Coupon(102L, 1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);

        LocalDateTime meetingDate = LocalDateTime.now().plusDays(1L);
        Reservation reservation1 = Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                2L, coupon1);
        Reservation reservation2 = Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                2L, coupon2);

        Reservations reservations = new Reservations(List.of(reservation1, reservation2));

        assertThat(reservations.getIds()).containsExactly(reservation1.getId(), reservation2.getId());
    }

    @DisplayName("예약된 쿠폰 id 들을 가져온다.")
    @Test
    void getCouponIds() {
        Coupon coupon1 = new Coupon(101L, 1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);
        Coupon coupon2 = new Coupon(102L, 1L, 2L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                CouponStatus.NOT_USED);

        LocalDateTime meetingDate = LocalDateTime.now().plusDays(1L);
        Reservation reservation1 = Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                2L, coupon1);
        Reservation reservation2 = Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING,
                2L, coupon2);

        Reservations reservations = new Reservations(List.of(reservation1, reservation2));

        assertThat(reservations.getCouponIds()).containsExactly(coupon1.getId(), coupon2.getId());
    }
}