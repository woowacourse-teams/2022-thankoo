package com.woowacourse.thankoo.reservation.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationRequest {

    private Long couponId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    public ReservationRequest(final long couponId, final LocalDateTime startAt) {
        this.couponId = couponId;
        this.startAt = startAt;
    }

}
