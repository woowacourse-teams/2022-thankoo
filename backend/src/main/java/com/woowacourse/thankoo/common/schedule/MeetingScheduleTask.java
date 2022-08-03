package com.woowacourse.thankoo.common.schedule;

import static com.woowacourse.thankoo.meeting.domain.MeetingStatus.FINISHED;
import static com.woowacourse.thankoo.meeting.domain.MeetingStatus.ON_PROGRESS;

import com.woowacourse.thankoo.meeting.domain.MeetingScheduleRepository;
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
        meetingScheduleRepository.updateMeetingStatus(FINISHED.name(), COMPLETE_JOB_DATE, ON_PROGRESS.name());
    }
}
