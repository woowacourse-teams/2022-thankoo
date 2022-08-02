package com.woowacourse.thankoo.reservation.presentation.dto;

import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.reservation.domain.ReservationCoupon;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationResponse {

    private Long reservationId;
    private String memberName;
    private String couponType;
    private TimeResponse time;

    private ReservationResponse(final Long reservationId,
                                final String memberName,
                                final String couponType,
                                final TimeResponse time) {
        this.reservationId = reservationId;
        this.memberName = memberName;
        this.couponType = couponType.toLowerCase(Locale.ROOT);
        this.time = time;
    }

    public static ReservationResponse from(final ReservationCoupon reservationCoupon) {
        return new ReservationResponse(reservationCoupon.getReservationId(),
                reservationCoupon.getMemberName(),
                reservationCoupon.getCouponType(),
                TimeResponse.from(reservationCoupon.getMeetingTime(), reservationCoupon.getTimeZone()));
    }
}
