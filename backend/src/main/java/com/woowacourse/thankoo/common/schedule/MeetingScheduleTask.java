package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.meeting.application.MeetingService;
import java.time.LocalDate;
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
public class MeetingScheduleTask {

    private final MeetingService meetingService;

    @Scheduled(cron = "0 0/30 10-19 * * *")
    public void executeCompleteMeeting() {
        try {
            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalTime nowTime = LocalTime.of(nowDateTime.getHour(), nowDateTime.getMinute());
            LocalDateTime meetingTime = LocalDateTime.of(nowDateTime.toLocalDate(), nowTime);
            meetingService.complete(meetingTime);
            log.info("[Scheduling] 당일 만남 완료 처리");
        } catch (RuntimeException e) {
            log.error("[Scheduling] 당일 만남 완료 처리 실패 : {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void executeSendMeetingMessage() {
        try {
            meetingService.sendMessageTodayMeetingMembers(LocalDate.now());
            log.info("[Scheduling] 만남 일정 슬랙 메세지 전송");
        } catch (RuntimeException e) {
            log.error("[Scheduling] 만남 일정 슬랙 메세지 전송 실패 {}", e.getMessage());
        }
    }
}
