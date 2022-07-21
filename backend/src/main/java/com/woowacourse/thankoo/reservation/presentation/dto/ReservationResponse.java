package com.woowacourse.thankoo.reservation.presentation.dto;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationResponse {

    private Long reservationId;
    private String name;
    private String couponType;
    private LocalDateTime meetingTime;

    public ReservationResponse(Long reservationId, String name, String couponType, LocalDateTime meetingTime) {
        this.reservationId = reservationId;
        this.name = name;
        this.couponType = couponType;
        this.meetingTime = meetingTime;
    }

    public static ReservationResponse from(Reservation reservation, Member foundMember) {
        return new ReservationResponse(reservation.getId(),
                foundMember.getName().getValue(),
                reservation.getCoupon().getCouponContent().getCouponType().name(),
                reservation.getMeetingTime());
    }
}
