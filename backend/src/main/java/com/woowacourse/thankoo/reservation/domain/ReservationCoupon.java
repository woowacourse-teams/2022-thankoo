package com.woowacourse.thankoo.reservation.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReservationCoupon {

    private final Long reservationId;
    private final String memberName;
    private final String couponType;
    private final LocalDateTime meetingTime;
    private final String timeZone;

    public ReservationCoupon(final Long reservationId,
                             final String memberName,
                             final String couponType,
                             final LocalDateTime meetingTime,
                             final String timeZone) {
        this.reservationId = reservationId;
        this.memberName = memberName;
        this.couponType = couponType;
        this.meetingTime = meetingTime;
        this.timeZone = timeZone;
    }
}
