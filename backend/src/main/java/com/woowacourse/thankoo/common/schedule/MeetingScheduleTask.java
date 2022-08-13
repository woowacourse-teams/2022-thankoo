package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.meeting.application.MeetingService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MeetingScheduleTask {

    public static final Long DAY = 1L;

    private final MeetingService meetingService;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void executeCompleteMeeting() {
        meetingService.complete(LocalDate.now().minusDays(DAY));
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void executeMeetingMessage() {
        meetingService.sendMessageTodayMeetingMembers();
    }
}
