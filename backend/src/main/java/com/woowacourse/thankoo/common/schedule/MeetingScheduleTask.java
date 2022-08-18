package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.meeting.application.MeetingService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class MeetingScheduleTask {

    public static final Long DAY = 1L;

    private final MeetingService meetingService;

    @Scheduled(cron = "0 0/30 10-20 * * *")
    public void executeCompleteMeeting() {
        meetingService.complete(LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void executeMeetingMessage() {
        meetingService.sendMessageTodayMeetingMembers(LocalDate.now());
    }
}
