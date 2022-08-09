package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.ReservationResponse;

public interface ReservationProvider {

    ReservationResponse getWaitingReservation(Long couponId);
}
