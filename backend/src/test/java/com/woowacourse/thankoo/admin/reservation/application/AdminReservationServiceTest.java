package com.woowacourse.thankoo.admin.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationRequest;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationResponse;
import com.woowacourse.thankoo.admin.reservation.domain.AdminReservationRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminReservationService 는 ")
@ApplicationTest
class AdminReservationServiceTest {

    @Autowired
    private AdminReservationService reservationService;

    @Autowired
    private AdminReservationRepository reservationRepository;

    @Autowired
    private AdminCouponRepository couponRepository;

    @DisplayName("모든 예약을 조회할 경우")
    @Nested
    class GetReservations {

        @BeforeEach
        void setUp() {
            Coupon coupon1 = saveCoupon();
            Coupon coupon2 = saveCoupon();
            Coupon coupon3 = saveCoupon();

            List.of(coupon1, coupon2, coupon3)
                    .forEach(coupon -> saveReservation(coupon.getReceiverId(), coupon));
        }

        @DisplayName("파라미터가 없는 경우 전체 조회를 한다.")
        @Test
        void getReservations() {
            AdminReservationRequest request = new AdminReservationRequest(null, null, null);
            List<AdminReservationResponse> reservations = reservationService.getReservations(request);

            assertThat(reservations).hasSize(3);
        }

        @DisplayName("시작일과 종료일, 상태를 기준으로 전체 조회를 한다. (모든 조건 만족하는 경우)")
        @Test
        void getReservationsWithStartBetweenEndAndStatus() {
            AdminReservationRequest request = new AdminReservationRequest(LocalDate.now(), LocalDate.now().plusDays(1L),
                    "WAITING");

            List<AdminReservationResponse> reservations = reservationService.getReservations(request);

            assertThat(reservations).hasSize(3);
        }

        @DisplayName("시작일과 종료일을 기준으로 전체 조회를 한다. (상태 조건이 없는 경우)")
        @Test
        void getReservationsWithStartEndTime() {
            AdminReservationRequest request = new AdminReservationRequest(LocalDate.now(), LocalDate.now().plusDays(1L),
                    null);

            List<AdminReservationResponse> reservations = reservationService.getReservations(request);

            assertThat(reservations).hasSize(3);
        }

        @DisplayName("시작일과 종료일을 기준으로 전체 조회를 한다. (조회일자 조건이 없는 경우)")
        @Test
        void getReservationsWithStatus() {
            AdminReservationRequest request = new AdminReservationRequest(null, null, "WAITING");

            List<AdminReservationResponse> reservations = reservationService.getReservations(request);

            assertThat(reservations).hasSize(3);
        }

        private Coupon saveCoupon() {
            return couponRepository.save(
                    new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, "호호의 커피쿠폰", "커피드세요."),
                            CouponStatus.NOT_USED));
        }

        private Reservation saveReservation(final Long memberId, final Coupon coupon) {
            return reservationRepository.save(
                    Reservation.reserve(LocalDateTime.now().plusDays(1L),
                            TimeZoneType.ASIA_SEOUL,
                            ReservationStatus.WAITING,
                            memberId,
                            coupon));
        }
    }

}