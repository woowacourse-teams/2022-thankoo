package com.woowacourse.thankoo.admin.reservation.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminReservationResponse {

    private final Long id;
    private final LocalDate date;
    private final LocalDateTime time;
    private final String timeZone;
    private final String status;
    private final Long memberId;
    private final Long couponId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public AdminReservationResponse(final Long id,
                                    final LocalDate date,
                                    final LocalDateTime time,
                                    final String timeZone,
                                    final String status,
                                    final Long memberId,
                                    final Long couponId,
                                    final LocalDateTime createdAt,
                                    final LocalDateTime modifiedAt) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.timeZone = timeZone;
        this.status = status;
        this.memberId = memberId;
        this.couponId = couponId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
