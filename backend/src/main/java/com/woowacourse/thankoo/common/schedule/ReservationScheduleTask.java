package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.reservation.application.ReservationService;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class ReservationScheduleTask {

    private final ReservationService reservationService;

    @Scheduled(cron = "0 0/30 10-19 * * *")
    void executeWaitingReservationCancel() {
        reservationService.cancel(ReservationStatus.WAITING, LocalDateTime.now());
    }
}
