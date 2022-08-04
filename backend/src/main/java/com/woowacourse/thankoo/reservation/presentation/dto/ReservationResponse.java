package com.woowacourse.thankoo.reservation.presentation.dto;

import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationResponse {

    private Long reservationId;
    private TimeResponse time;
    private String status;

    public ReservationResponse(final Long reservationId, final TimeResponse time, final String status) {
        this.reservationId = reservationId;
        this.time = time;
        this.status = status.toLowerCase(Locale.ROOT);
    }

    public static ReservationResponse of(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                TimeResponse.of(reservation.getMeetingTime()),
                reservation.getReservationStatus().name());
    }
}
