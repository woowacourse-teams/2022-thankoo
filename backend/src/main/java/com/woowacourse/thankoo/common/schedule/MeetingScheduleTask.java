package com.woowacourse.thankoo.common.schedule;

import com.woowacourse.thankoo.meeting.domain.MeetingScheduleRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MeetingScheduleTask {

    public static final LocalDate COMPLETE_JOB_DATE = LocalDate.now().minusDays(1L);

    private final MeetingScheduleRepository meetingScheduleRepository;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void executeCompleteMeeting() {
        meetingScheduleRepository.updateMeetingStatus(MeetingStatus.FINISHED.name(), COMPLETE_JOB_DATE, MeetingStatus.ON_PROGRESS.name());
    }
}
