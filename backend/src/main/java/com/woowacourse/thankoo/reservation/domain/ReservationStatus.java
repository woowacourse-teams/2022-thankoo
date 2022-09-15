package com.woowacourse.thankoo.reservation.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.util.Arrays;

public enum ReservationStatus {

    WAITING,
    DENY,
    ACCEPT,
    CANCELED;

    public static ReservationStatus from(final String status) {
        return Arrays.stream(values())
                .filter(reservationStatus -> reservationStatus.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new InvalidReservationException(ErrorType.NOT_FOUND_RESERVATION_STATUS));
    }

    public boolean isWaiting() {
        return this == WAITING;
    }

    public boolean isDeny() {
        return this == DENY;
    }
}
