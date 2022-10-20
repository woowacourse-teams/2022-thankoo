package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.coupon.domain.CouponImmediatelyUsedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationEventListener {

    private final ReservationService reservationService;

    @EventListener
    public void handle(final CouponImmediatelyUsedEvent couponImmediatelyUsedEvent) {
        reservationService.cancelReservation(couponImmediatelyUsedEvent.getCouponId());
    }
}
