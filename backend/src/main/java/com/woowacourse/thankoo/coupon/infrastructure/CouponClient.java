package com.woowacourse.thankoo.coupon.infrastructure;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.meeting.exception.InvalidMeetingException;
import com.woowacourse.thankoo.meeting.presentation.dto.MeetingResponse;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import com.woowacourse.thankoo.reservation.presentation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class CouponClient {

    private final ReservationRepository reservationRepository;
    private final MeetingRepository meetingRepository;

    public ReservationResponse getReservationResponse(final Long couponId) {
        return ReservationResponse.of(
                reservationRepository.findTopByCouponIdAndReservationStatus(couponId, ReservationStatus.WAITING)
                        .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION)));
    }

    public MeetingResponse getMeetingResponse(final Long couponId) {
        return MeetingResponse.of(
                meetingRepository.findTopByCouponIdAndMeetingStatus(couponId, MeetingStatus.ON_PROGRESS)
                        .orElseThrow(() -> new InvalidMeetingException(ErrorType.NOT_FOUND_MEETING)));
    }
}
