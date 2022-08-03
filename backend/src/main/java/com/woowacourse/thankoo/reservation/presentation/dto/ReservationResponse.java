package com.woowacourse.thankoo.reservation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.thankoo.reservation.domain.ReservationCoupon;
import java.time.LocalDateTime;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingTime;

    private ReservationResponse(final Long reservationId,
                                final String memberName,
                                final String couponType,
                                final LocalDateTime meetingTime) {
        this.reservationId = reservationId;
        this.memberName = memberName;
        this.couponType = couponType.toLowerCase(Locale.ROOT);
        this.meetingTime = meetingTime;
    }

    public static ReservationResponse from(final ReservationCoupon reservationCoupon) {
        return new ReservationResponse(reservationCoupon.getReservationId(),
                reservationCoupon.getMemberName(),
                reservationCoupon.getCouponType(),
                reservationCoupon.getMeetingTime());
    }
}
