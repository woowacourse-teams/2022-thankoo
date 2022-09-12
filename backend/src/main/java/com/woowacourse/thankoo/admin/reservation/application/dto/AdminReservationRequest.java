package com.woowacourse.thankoo.admin.reservation.application.dto;

import java.time.LocalDate;

public class AdminReservationRequest {

    private final LocalDate start;
    private final LocalDate end;
    private final String status;

    public AdminReservationRequest(final LocalDate start, final LocalDate end, final String status) {
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getStatus() {
        return status;
    }
}
