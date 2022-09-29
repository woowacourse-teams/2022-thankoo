package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.CouponCompleteEvent;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class CouponEventListener {

    private final ReservationRepository reservationRepository;

    @EventListener
    public void complete(final CouponCompleteEvent event) {
        Reservation reservation = getReservation(event.getCouponId());
        reservation.complete();
    }

    private Reservation getReservation(final Long couponId) {
        return reservationRepository.findTopByCouponIdAndReservationStatus(couponId, ReservationStatus.WAITING)
                .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION_STATUS));
    }
}
