package com.woowacourse.thankoo.common.schedule;

import static com.woowacourse.thankoo.meeting.domain.MeetingStatus.ON_PROGRESS;

import com.woowacourse.thankoo.alarm.support.Alarm;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.Coupons;
import com.woowacourse.thankoo.meeting.application.MeetingService;
import com.woowacourse.thankoo.meeting.domain.MeetingRepository;
import com.woowacourse.thankoo.meeting.domain.MeetingStatus;
import com.woowacourse.thankoo.meeting.domain.Meetings;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MeetingScheduleTask {

    public static final Long DAY = 1L;

    private final MeetingService meetingService;
    private final MeetingRepository meetingRepository;
    private final CouponRepository couponRepository;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void executeCompleteMeeting() {
        Meetings meetings = new Meetings(meetingRepository.findAllByMeetingStatusAndTimeUnit_Date(
                ON_PROGRESS, LocalDate.now().minusDays(DAY)));
        Coupons coupons = new Coupons(meetings.getCoupons());

        meetingRepository.updateMeetingStatus(MeetingStatus.FINISHED, meetings.getMeetingIds());
        couponRepository.updateCouponStatus(CouponStatus.USED, coupons.getCouponIds());
    }

    @Alarm
    @Scheduled(cron = "0 0 9 * * *")
    public void executeMeetingMessage() {
        meetingService.sendMessageTodayMeetingMembers();
    }
}
