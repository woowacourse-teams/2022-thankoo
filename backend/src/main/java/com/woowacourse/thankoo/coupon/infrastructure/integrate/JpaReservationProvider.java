package com.woowacourse.thankoo.coupon.infrastructure.integrate;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.infrastructure.ReservationProvider;
import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.ReservationResponse;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaReservationProvider implements ReservationProvider {

    private final ReservationRepository reservationRepository;

    @Override
    public ReservationResponse getWaitingReservation(final Long couponId) {
        return ReservationResponse.of(
                reservationRepository.findTopByCouponIdAndReservationStatus(couponId, ReservationStatus.WAITING)
                        .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION)));
    }
}
