package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.meeting.application.MeetingService;
import java.time.LocalDate;
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
public class MeetingScheduleTask {

    private final MeetingService meetingService;

    @Scheduled(cron = "0 0/30 10-19 * * *")
    public void executeCompleteMeeting() {
        log.debug("[Scheduling] 당일 만남 완료 처리");
        meetingService.complete(LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void executeSendMeetingMessage() {
        log.debug("[Scheduling] 만남 일정 슬랙 메세지 전송");
        meetingService.sendMessageTodayMeetingMembers(LocalDate.now());
    }
}
