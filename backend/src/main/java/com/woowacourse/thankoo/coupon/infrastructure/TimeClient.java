package com.woowacourse.thankoo.coupon.infrastructure;

import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponStatusException;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class TimeClient {

    private final ReservationRepository reservationRepository;
    private final MeetingRepository meetingRepository;

    public TimeResponse getTimeResponse(final Long couponId, final CouponStatus couponStatus) {
        validateCouponStatus(couponStatus);
        if (couponStatus.isReserving()) {
            return TimeResponse.of(getReservation(couponId).getMeetingTime());
        }
        return TimeResponse.of(getMeeting(couponId).getMeetingTime());
    }

    private void validateCouponStatus(final CouponStatus couponStatus) {
        if (!couponStatus.isInReserve()) {
            throw new InvalidCouponStatusException(ErrorType.INVALID_COUPON_STATUS);
        }
    }

    private Reservation getReservation(final Long couponId) {
        return reservationRepository.findTopByCoupon_IdAndReservationStatus(couponId, ReservationStatus.WAITING)
                .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION));
    }

    private Meeting getMeeting(final Long couponId) {
        return meetingRepository.findTopByCoupon_IdAndMeetingStatus(couponId, MeetingStatus.ON_PROGRESS)
                .orElseThrow(() -> new InvalidMeetingException(ErrorType.NOT_FOUND_MEETING));
    }
}
