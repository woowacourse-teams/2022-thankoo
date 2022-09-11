package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.reservation.application.ReservationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationScheduleTask {

    private final ReservationService reservationService;

    @Scheduled(cron = "0 0/30 10-19 * * *")
    void executeCancelExpiredReservation() {
        log.debug("[Scheduling] 만료된 예약 취소 처리");
        reservationService.cancelExpiredReservation(LocalDateTime.now());
    }
}
