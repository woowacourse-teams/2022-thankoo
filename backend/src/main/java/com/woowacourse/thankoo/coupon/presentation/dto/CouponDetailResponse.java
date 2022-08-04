package com.woowacourse.thankoo.coupon.presentation.dto;

import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.meeting.presentation.dto.MeetingResponse;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponDetailResponse {

    private CouponResponse coupon;
    private ReservationResponse reservation;
    private MeetingResponse meeting;

    private CouponDetailResponse(final CouponResponse coupon,
                                 final ReservationResponse reservation,
                                 final MeetingResponse meeting) {
        this.coupon = coupon;
        this.reservation = reservation;
        this.meeting = meeting;
    }

    public static CouponDetailResponse from(final MemberCoupon memberCoupon, final MeetingResponse meetingResponse) {
        return new CouponDetailResponse(CouponResponse.of(memberCoupon), null, meetingResponse);
    }

    public static CouponDetailResponse from(final MemberCoupon memberCoupon,
                                            final ReservationResponse reservationResponse) {
        return new CouponDetailResponse(CouponResponse.of(memberCoupon), reservationResponse, null);
    }

    public static CouponDetailResponse of(final MemberCoupon memberCoupon) {
        return new CouponDetailResponse(CouponResponse.of(memberCoupon), null, null);
    }
}
