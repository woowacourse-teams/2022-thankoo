package com.woowacourse.thankoo.meeting.domain;

import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MeetingCoupon {

    private final Long id;
    private final MeetingTime meetingTime;
    private final String couponType;
    private final String memberName;

    public MeetingCoupon(final Long id,
                         final LocalDateTime meetingTime,
                         final TimeZoneType timeZone,
                         final String couponType,
                         final String memberName) {
        this.id = id;
        this.meetingTime = new MeetingTime(meetingTime.toLocalDate(), meetingTime, timeZone.getId());
        this.couponType = couponType;
        this.memberName = memberName;
    }
}
