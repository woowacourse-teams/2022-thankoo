package com.woowacourse.thankoo.reservation.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationStatusRequest {

    private String status;

    public ReservationStatusRequest(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReservationStatusRequest{" +
                "status='" + status + '\'' +
                '}';
    }
}
