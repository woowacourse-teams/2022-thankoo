package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.reservation.application.ReservationService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile({"prod", "dev"})
@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationScheduleTask {

    private final ReservationService reservationService;

    @Scheduled(cron = "0 0/30 10-19 * * *")
    void executeCancelExpiredReservation() {
        try {
            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalTime nowTime = LocalTime.of(nowDateTime.getHour(), nowDateTime.getMinute());
            LocalDateTime reservationTime = LocalDateTime.of(nowDateTime.toLocalDate(), nowTime);
            reservationService.cancelExpiredReservation(reservationTime);
            log.info("[Scheduling] 만료된 예약 취소 처리");
        } catch (RuntimeException e) {
            log.error("[Scheduling] 만료된 예약 취소 처리 실패");
        }
    }
}
